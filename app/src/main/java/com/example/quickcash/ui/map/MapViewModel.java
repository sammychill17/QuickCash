package com.example.quickcash.ui.map;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quickcash.FirebaseStuff.JobDBHelper;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.UserLocation;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapViewModel extends ViewModel {
    private MutableLiveData<Integer> selectedDistance = new MutableLiveData<>();
    private MutableLiveData<List<Job>> jobs = new MutableLiveData<>();
    private JobDBHelper jobDBHelper = new JobDBHelper();
    private MutableLiveData<UserLocation> userLocation = new MutableLiveData<>();

    public LiveData<Integer> getSelectedDistance() {
        return selectedDistance;
    }

    public void setSelectedDistance(int distance) {
        selectedDistance.setValue(distance);
    }

    public LiveData<List<Job>> getJobs() {
        return jobs;
    }

    /*
    not used
     */
    public void fetchJobs() {
        jobDBHelper.getAllJobsWithCoordinates(new JobDBHelper.JobListCallback() {
            @Override
            public void onJobsReceived(List<Job> jobList) {
                /*
                 assuming userLocation is already set by the time this method is called
                 */
                UserLocation currentLocation = userLocation.getValue();
                Integer distanceFilter = selectedDistance.getValue();
                if (currentLocation != null && distanceFilter != null) {
                    List<Job> filteredJobs = filterJobsByDistance(jobList, currentLocation, distanceFilter);
                    jobs.setValue(filteredJobs);
                } else {
                    /*
                    this will hold all jobs instead of separate job types
                     */
                    jobs.setValue(jobList);
                }
            }

            @Override
            public void onError(DatabaseError error) {
                jobs.setValue(new ArrayList<>());
            }
        });
    }

    private List<Job> filterJobsByDistance(List<Job> jobList, UserLocation userLocation, int maxDistance) {
        List<Job> filteredJobs = new ArrayList<>();
        for (Job job : jobList) {
            double distance = calculateDistance(userLocation.getLatitude(), userLocation.getLongitude(),
                    job.getLatitude(), job.getLongitude());
            if (distance <= maxDistance) {
                filteredJobs.add(job);
            }
        }
        return filteredJobs;
    }

    /*
    calculates or at least tries to calculates the distance
     */
    private double calculateDistance(double startLat, double startLng, double endLat, double endLng) {
        float[] results = new float[1];
        Location.distanceBetween(startLat, startLng, endLat, endLng, results);
        return results[0];
    }

    public void fetchJobs(UserLocation userCurrentLocation, int maxDistance) {
        jobDBHelper.getAllJobsWithCoordinates(new JobDBHelper.JobListCallback() {
            @Override
            public void onJobsReceived(List<Job> jobList) {
                /*
                filters the jobs based on maxDistance before posting the value
                 */
                List<Job> filteredJobs = filterJobsByDistance(jobList, maxDistance, userCurrentLocation);
                jobs.postValue(filteredJobs);
            }

            @Override
            public void onError(DatabaseError error) {
                /*
                 handles the error, maybe post a message or an empty list
                 */
                jobs.postValue(Collections.emptyList());
            }
        });
    }


    // Method to filter jobs based on distance
    private List<Job> filterJobsByDistance(List<Job> jobList, int maxDistance, UserLocation currentLocation) {
        if (currentLocation == null) {
            return Collections.emptyList();
        }

        Location currentUserLocation = new Location("");
        currentUserLocation.setLatitude(currentLocation.getLatitude());
        currentUserLocation.setLongitude(currentLocation.getLongitude());

        List<Job> filteredJobs = new ArrayList<>();
        for (Job job : jobList) {
            Location jobLocation = new Location("");
            jobLocation.setLatitude(job.getLatitude());
            jobLocation.setLongitude(job.getLongitude());

            if (currentUserLocation.distanceTo(jobLocation) <= maxDistance) {
                filteredJobs.add(job);
            }
        }
        return filteredJobs;
    }

}
