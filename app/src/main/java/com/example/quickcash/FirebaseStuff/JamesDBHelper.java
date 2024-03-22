package com.example.quickcash.FirebaseStuff;

import android.util.Log;
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
    EmployerDBHelper employerDBHelper = new EmployerDBHelper();

    ArrayList<Employee> returnList;

    Set<String> emailList = new HashSet<>();

    ArrayList<JobApplicants> applicantObjectList = new ArrayList<>();

    List<Job> jobList;

    ArrayList<String> keyList = new ArrayList<>();
    private int setApplicantsObjectListSemaphore = 0;
    private int setEmployeeObjectListSemaphore = 0;

    public JamesDBHelper(){
        email = "";
        returnList = new ArrayList<>();
    }

    public JamesDBHelper(String email){
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
        return returnList;
    }

    public static class EmployerDBHelperCallback{
        public void onResult(List<Job> jobs){}
    }
    public void setJobList(String email, EmployerDBHelperCallback callback) {
        EmployerDBHelper employerDBHelper = new EmployerDBHelper();
        employerDBHelper.getJobsByEmployer(email, new EmployerDBHelper.JobObjectCallback() {
            @Override
            public void onJobsReceived(List<Job> jobs) {
                jobList = jobs;
                callback.onResult(jobs);
            }

            @Override
            public void onError(DatabaseError error) {

            }
        });
    }

    public void setKeyList(){
        keyList.clear();
        for (Job job : jobList) {
            keyList.add(job.getKey());
        }
    }

    public static class JobDBHelperCallback{

        public void onResult(List<JobApplicants> applicants){}

    }
    public void setApplicantObjectList(JobDBHelperCallback callback){
        JobDBHelper jobDBHelper = new JobDBHelper();
        setApplicantsObjectListSemaphore = keyList.size();
        for (String key: keyList) {
            jobDBHelper.getApplicantsByKey(key, new JobDBHelper.ApplicantsObjectCallback() {
                @Override
                public void onObjectReceived(JobApplicants object) {
                    applicantObjectList.add(object);
                    setApplicantsObjectListSemaphore--;
                    if (setApplicantsObjectListSemaphore == 0) {
                        // Call callback
                        callback.onResult(applicantObjectList);
                    }
                }

                @Override
                public void onError(DatabaseError error) {
                    setApplicantsObjectListSemaphore--;

                    if (setApplicantsObjectListSemaphore == 0) {
                        // Call callback
                        callback.onResult(applicantObjectList);
                    }
                }
            });
        }
    }

    public void setEmailList(){
        for(JobApplicants applicant : applicantObjectList) {
            emailList.addAll(applicant.getApplicants());
        }
    }

    public static class DatabaseScroungerCallback{

        public void onResult(List<Employee> list){}
    }

    public void setReturnList(DatabaseScroungerCallback callback){
        DatabaseScrounger dbScrounger = new DatabaseScrounger("Users");
        setEmployeeObjectListSemaphore = emailList.size();
        for(String email: emailList) {
            dbScrounger.getEmployeeByEmail(email, new DatabaseScrounger.EmployeeCallback() {
                @Override
                public void onObjectReceived(Employee object) {
                    returnList.add(object);
                    setEmployeeObjectListSemaphore--;
                    if(setEmployeeObjectListSemaphore == 0){
                        callback.onResult(returnList);
                    }
                }

                @Override
                public void onError(DatabaseError error) {
                    setEmployeeObjectListSemaphore--;
                    if(setEmployeeObjectListSemaphore == 0){
                        callback.onResult(returnList);
                    }
                }
            });
        }
    }

    public void runHelper(DatabaseScroungerCallback callback){
        setJobList(email, new EmployerDBHelperCallback() {
            @Override
            public void onResult(List<Job> jobs) {
                setKeyList();
                setApplicantObjectList(new JobDBHelperCallback() {
                    @Override
                    public void onResult(List<JobApplicants> list) {
                        setEmailList();
                        setReturnList(new DatabaseScroungerCallback() {
                            @Override
                            public void onResult(List<Employee> list){
                                callback.onResult(list);
                            }

                        });
                    }
                });
            }
        });
    }
}
