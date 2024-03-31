package com.example.quickcash.FirebaseStuff;

import com.example.quickcash.Objects.Employee;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.JobApplicants;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class 'JamesDBHelper' is a DatabaseHelper function designed to query from multiple
 * data objects in the database to get a set of all unique Employee objects who are
 * applied to any and all jobs posted by the given employer email.
 *
 * This is used primarily and exclusively by the MyEmployeesActivity activity,
 * and was made due to the high number of tasks required to retrieve this information.
 *
 * Due to the fact that Applicants and Employers are not stored together directly,
 * in order to gather this information, the Posted Jobs table must be queried
 * (using EmployerDBHelper) to get all associated jobs with an employer (this is jobList).
 *
 * Then, the job keys are extracted from the jobs (keyList) and used (via JobDBHelper) to gat a
 * list of all JobApplicants objects for the associated jobs to the employer
 * (this is applicantObjectList).
 *
 * Then, the emails of all unique applicants are gathered from those objects and are
 * placed in a set of emails (this is emailList).
 *
 * Those emails are then used (by DatabaseScrounger) to get a list of Employee Objects
 * that are associated with the unique email (this is the returnList).
 *
 * All of these lists are populated and callbacks are used with the runHelper() method.
 *
 *
 * The name JamesDBHelper is a reference to James Gaultois, who was one of the developers for
 * this application who had a penchant for creating DatabaseHelper and DatabaseScrounger classes.
 *
 * The majority of the ones used in this application were made and used by him.
 */
public class JamesDBHelper {
    String email;
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
            if(applicant!=null) {
                emailList.addAll(applicant.getApplicants());
            }
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
