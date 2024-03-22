package com.example.quickcash.FirebaseStuff;

import android.widget.Toast;

import com.example.quickcash.Objects.Employee;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.JobApplicants;
import com.example.quickcash.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JamesDBHelper {

    String email;
    FirebaseDatabase database;

    EmployerDBHelper employerDBHelper = new EmployerDBHelper();

    ArrayList<Employee> returnList;

    Set<String> emailList = new HashSet<>();

    ArrayList<JobApplicants> applicantObjectList = new ArrayList<>();

    public JamesDBHelper(){
        database = FirebaseDatabase.getInstance("https://quickcash-6941c-default-rtdb.firebaseio.com/");
        email = "";
        returnList = new ArrayList<>();
    }

    public JamesDBHelper(String email){
        database = FirebaseDatabase.getInstance("https://quickcash-6941c-default-rtdb.firebaseio.com/");
        this.email = email;
        returnList = new ArrayList<>();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Employee> getReturnList() {
        ArrayList<Job> jobList = new ArrayList<>();
        employerDBHelper.getJobsByEmployer(email, new EmployerDBHelper.JobObjectCallback() {
            @Override
            public void onJobsReceived(List<Job> jobs) {
                jobList.addAll(jobs);
            }

            @Override
            public void onError(DatabaseError error) {

            }
        });

        JobDBHelper jobDBHelper = new JobDBHelper();

        for (int i = 0; i < jobList.size(); i++) {
            jobDBHelper.getApplicantsByKey(jobList.get(i).getKey(), new JobDBHelper.ApplicantsObjectCallback() {
                @Override
                public void onObjectReceived(JobApplicants object) {
                    applicantObjectList.add(object);
                }

                @Override
                public void onError(DatabaseError error) {

                }
            });
        }

        for (int i = 0; i < applicantObjectList.size(); i++) {
            emailList.addAll(applicantObjectList.get(i).getApplicants());
        }

        DatabaseScrounger dbScrounger = new DatabaseScrounger("Users");

        Object[] emailArray = emailList.toArray();
        for (int i = 0; i < emailList.size(); i++) {
            String employeeEmail = (String) emailArray[i];
            dbScrounger.getEmployeeByEmail(employeeEmail, new DatabaseScrounger.EmployeeCallback() {
                @Override
                public void onObjectReceived(Employee object) {
                    returnList.add(object);
                }

                @Override
                public void onError(DatabaseError error) {

                }
            });
        }

        return returnList;
    }

}
