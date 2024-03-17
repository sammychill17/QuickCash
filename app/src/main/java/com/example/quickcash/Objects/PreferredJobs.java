package com.example.quickcash.Objects;

import com.example.quickcash.FirebaseStuff.QuickCashDBObject;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

/*
* This class is designed to store the PreferredJobs object which keeps track of which jobs an employee prefers.
 */
public class PreferredJobs extends QuickCashDBObject {
    private String employeeEmail;
    private List<String> preferredJobs;

    public PreferredJobs(String email) {
        this.employeeEmail = email;
        this.preferredJobs = new ArrayList<>();
    }

    public void checkJob(JobTypes type) {
        if (type != JobTypes.UNDEFINED && !preferredJobs.contains(type.name())) {
            preferredJobs.add(type.name());
        }
    }

    public void uncheckJob(JobTypes type) {
        preferredJobs.remove(type.name());
    }

    public List<String> getPreferredJobs() {
        return preferredJobs;
    }

    public void setPreferredJobs(List<String> preferredJobs) {
        this.preferredJobs = preferredJobs;
    }

    public String getEmail() {
        return employeeEmail;
    }

    public void setEmail(String email) {
        this.employeeEmail = email;
    }

    public boolean doesPreferred(String jobName) {
        return preferredJobs.contains(jobName);
    }

    /*
     this is a helper method that converts list of job type names to list of JobTypes
     */
    public static List<JobTypes> convertNamesToJobTypes(List<String> names) {
        List<JobTypes> jobTypes = new ArrayList<>();
        for (String name : names) {
                jobTypes.add(JobTypes.valueOf(name));
            }
        return jobTypes;
    }

    /*
    another method thats gonna be "helping" us,
    this method will convert list of JobTypes to list of job type names
     */

    public static List<String> convertJobTypesToNames(List<JobTypes> jobTypes) throws IllegalArgumentException {
        List<String> names = new ArrayList<>();
        for (JobTypes jobType : jobTypes) {
            names.add(jobType.name());
        }
        return names;
    }

    public boolean containsJob(JobTypes jobTypes){
        return preferredJobs.contains(jobTypes.name());
    }

}
