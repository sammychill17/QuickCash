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
 * This class 'ApplicantDBHelper' is a DatabaseHelper function designed to query from multiple
 * data objects in the database to get a set of all unique Employee objects who are
 * applied and accepted to any and all jobs posted by the given employer email.
 *
 * This is used primarily and exclusively by the MyEmployeesActivity activity,
 * and was made due to the high number of tasks required to retrieve this information.
 *
 * It starts by gathering all jobs related to a specific employer, and then it grabs all of the emails
 * of all chosen applicants from all of those jobs.
 *
 * Then it converts those applicants back into users before returning them for use in the MyEmployees activity.
 *
 * The majority of the ones used in this application were made and used by him.
 */
public class ApplicantDBHelper {
    String email;
    ArrayList<Employee> returnList;
    Set<String> emailList = new HashSet<>();
    ArrayList<JobApplicants> applicantObjectList = new ArrayList<>();
    List<Job> jobList;
    ArrayList<String> chosenApplicantList = new ArrayList<>();
    private int setEmployeeObjectListSemaphore = 0;
    public ApplicantDBHelper(){
        email = "";
        returnList = new ArrayList<>();
    }

    public ApplicantDBHelper(String email){
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
        public void onResult(List<Job> jobs){
            /*
             * This is overwritten in later callings of this method and as such is blank when declared here.
             */
        }
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
                /*
                * We are swallowing this error.
                 */
            }
        });
    }

    public void setChosenApplicantList(){
        chosenApplicantList.clear();
        for (Job job : jobList) {
            chosenApplicantList.add(job.getAssigneeEmail());
        }
    }

    public void setEmailList(){
        for(String applicant : chosenApplicantList) {
            if(applicant!=null) {
                emailList.add(applicant);
            }
        }
    }

    public static class DatabaseScroungerCallback{

        public void onResult(List<Employee> list){
            /*
             * This is empty as it has to be overwritten in future callings of this method.
             */
        }
    }

    public void setReturnList(DatabaseScroungerCallback callback){
        returnList.clear();
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
                setChosenApplicantList();
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
}
