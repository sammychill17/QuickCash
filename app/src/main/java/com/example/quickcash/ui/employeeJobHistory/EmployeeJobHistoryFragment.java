package com.example.quickcash.ui.employeeJobHistory;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.quickcash.R;
import com.google.firebase.database.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;

public class EmployeeJobHistoryFragment extends Fragment {

    private ListView previousJobListView;
    private ListView upcomingJobListView;
    private SimpleAdapter previousAdapter;
    private SimpleAdapter upcomingAdapter;
    private List<Map<String, String>> previousJobList;
    private List<Map<String, String>> upcomingJobList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_employeejobhistory, container, false);

        previousJobListView = rootView.findViewById(R.id.previousJobListView);
        upcomingJobListView = rootView.findViewById(R.id.upcomingJobListView);
        previousJobList = new ArrayList<>();
        upcomingJobList = new ArrayList<>();
        int listItemLayout = R.layout.job_history_item;
        String[] from = {"title", "description", "date"};
        int[] to = {R.id.jobTitle, R.id.jobDescription, R.id.jobDate};
        previousAdapter = new SimpleAdapter(getContext(), previousJobList, listItemLayout, from, to);
        upcomingAdapter = new SimpleAdapter(getContext(), upcomingJobList, listItemLayout, from, to);
        previousJobListView.setAdapter(previousAdapter);
        upcomingJobListView.setAdapter(upcomingAdapter);

        loadJobsFromFirebase();
        Button backButton = rootView.findViewById(R.id.backFromHistory);
        backButton.setOnClickListener(view -> {
            getParentFragmentManager().popBackStack();
        });
        return rootView;
    }
    /*
     * Retrieve and load jobs of the current employee from the database
     * it will load both previous and upcoming jobs by comparing -
     * epoch values.
     */
    private void loadJobsFromFirebase() {
        /*
         retrieves the employee's email from SharedPreferences via
         getEmployeeEmailFromPreferences method
         */
        String employeeEmail = getEmployeeEmailFromSharedPreferences();
        Log.d("EmployeeJobHistory", "loading deez jobs for- " + employeeEmail);

        /*
        makes a reference to the 'Posted Jobs' branch
        in the firebase database.
         */
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Posted Jobs");
        databaseReference.orderByChild("assigneeEmail").equalTo(employeeEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Log.d("EmployeeJobHistory", "No jobs were found for- " + employeeEmail + ", how sad :(");
                    return;
                }
                /*
                this clears the current lists (for prev/upcoming jobs)
                to prepare for new data.
                 */
                previousJobList.clear();
                upcomingJobList.clear();

                /*
                loop through the snapshots of each job posted accordingly
                 */
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    /*
                    extract the 'assigned' status and the epoch time of the job
                     */
                    Boolean assigned = snapshot.child("assigned").getValue(Boolean.class);
                    Long epoch = snapshot.child("epoch").getValue(Long.class);
                    /*
                    checks if the job is assigned and the epoch time is not null
                     */
                    if (ifJobAssignedValid(epoch, assigned)) {
                        /*
                        this creates a map to hold the job data
                         */
                        HashMap<String, String> jobData = new HashMap<>();
                        jobData.put("title", snapshot.child("title").getValue(String.class));
                        jobData.put("description", snapshot.child("description").getValue(String.class));
                        /*
                        retrieves and formats the date
                         */
                        Long time = snapshot.child("date/time").getValue(Long.class);
                        String formattedDate = formatDate(time);
                        jobData.put("date", formattedDate);
                        /*
                        get the current system (of the user's) time
                         */
                        long currentTime = System.currentTimeMillis();
                        /*
                         checks if the job is past or upcoming
                         and add it to the corresponding list.
                         */
                        if (epoch < currentTime) {
                            previousJobList.add(jobData);
                        } else {
                            upcomingJobList.add(jobData);
                        }
                    }
                }

                if (previousJobList.isEmpty()) {
                    TextView emptyTextView = (TextView) getView().findViewById(R.id.emptyPreviousTextView);
                    emptyTextView.setVisibility(View.VISIBLE);
                }
                else {
                    TextView emptyTextView = (TextView) getView().findViewById(R.id.emptyPreviousTextView);
                    emptyTextView.setVisibility(View.INVISIBLE);
                }

                if (upcomingJobList.isEmpty()) {
                    TextView emptyTextView = (TextView) getView().findViewById(R.id.emptyUpcomingTextView);
                    emptyTextView.setVisibility(View.VISIBLE);
                }
                else {
                    TextView emptyTextView = (TextView) getView().findViewById(R.id.emptyUpcomingTextView);
                    emptyTextView.setVisibility(View.INVISIBLE);
                }
                /*
                this notifies the adapters that the data has changed
                so the UI can be updated accordingly
                 */
                previousAdapter.notifyDataSetChanged();
                upcomingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("EmployeeJobHistory", "database error!!!!! WHY", databaseError.toException());
            }
        });
    }
    /*
    this is a helper method to grab logged in user's email
    from shared preferences
     */
    private String getEmployeeEmailFromSharedPreferences() {
        SharedPreferences sharedPrefs = requireContext().getSharedPreferences("session_login", MODE_PRIVATE);
        return sharedPrefs.getString("email", "");
    }

    /*
    this is a helper method to format date
    formats the date with a simple date format,
    displaying year, month, and day
     */
    private String formatDate(Long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }

    private boolean ifJobAssignedValid(Long epoch, Boolean assigned){
        if(assigned == null){
            return false;
        }
        else if(epoch == null){
            return false;
        }
        else{
            return assigned;
        }
    }
}
