package com.example.quickcash.Objects;

import com.example.quickcash.FirebaseStuff.QuickCashDBObject;

public class PreferredJobs extends QuickCashDBObject {
    private String employeeEmail;
    private JobTypes[] preferredJobs = new JobTypes[11];

    public PreferredJobs(String email){
        employeeEmail = email;
        for (int i = 0; i < 11; i++) {
            preferredJobs[i] = JobTypes.UNDEFINED;
        }
    }

    public void checkJob(JobTypes type){
        if(type != JobTypes.UNDEFINED){
            for (int i = 0; i < 11; i++) {
                if(preferredJobs[i]==JobTypes.UNDEFINED){
                    preferredJobs[i] = type;
                    break;
                }
            }
        }
    }
    public void uncheckJob(JobTypes type){
        if(type != JobTypes.UNDEFINED){
            for (int i = 0; i < 11; i++) {
                if(preferredJobs[i]==type){
                    preferredJobs[i] = JobTypes.UNDEFINED;
                    break;
                }
            }
        }
    }

    public boolean doesPreferred(JobTypes type){
        if(type != JobTypes.UNDEFINED){
            for (int i = 0; i < 11; i++) {
                if(preferredJobs[i]==type){
                    return true;
                }
            }
            return false;
        }
        else{
            return false;
        }
    }

    public JobTypes[] getPreferredJobs(){
        return preferredJobs;
    }

    public String getEmail(){
        return employeeEmail;
    }
}
