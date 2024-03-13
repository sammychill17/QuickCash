package com.example.quickcash.Objects;

import com.example.quickcash.FirebaseStuff.QuickCashDBObject;

import java.util.ArrayList;
import java.util.List;

public class PreferredJobs extends QuickCashDBObject {
    private int enumSize = 11;
    private String employeeEmail;
    private ArrayList<JobTypes> preferredJobs = new ArrayList<>();

    public PreferredJobs(String email){
        employeeEmail = email;
        for (int i = 0; i < enumSize; i++) {
            preferredJobs.add(JobTypes.UNDEFINED);
        }
    }

    public static List<JobTypes> convertNamesToJobTypes(List<String> names) {
        List<JobTypes> returnList = new ArrayList<JobTypes>();
        for (int i = 0; i < names.size(); i++) {
            String currName = names.get(i);
            if(currName.matches("ARTS_CREATIVE")){
                returnList.add(JobTypes.ARTS_CREATIVE);
            }
            else if(currName.matches("BABYSITTING")){
                returnList.add(JobTypes.BABYSITTING);
            }
            else if(currName.matches("COOK")){
                returnList.add(JobTypes.COOK);
            }
            else if(currName.matches("HITMAN")){
                returnList.add(JobTypes.HITMAN);
            }
            else if(currName.matches("MAGICIAN")){
                returnList.add(JobTypes.MAGICIAN);
            }
            else if(currName.matches("MOVING")){
                returnList.add(JobTypes.MOVING);
            }
            else if(currName.matches("PETCARE")){
                returnList.add(JobTypes.PETCARE);
            }
            else if(currName.matches("POLITICIAN")){
                returnList.add(JobTypes.POLITICIAN);
            }
            else if(currName.matches("TECH")){
                returnList.add(JobTypes.TECH);
            }
            else if(currName.matches("TUTORING")){
                returnList.add(JobTypes.TUTORING);
            }
            else if(currName.matches("YARDWORD")){
                returnList.add(JobTypes.YARDWORK);
            }
            else{
                returnList.add(JobTypes.UNDEFINED);
            }
        }
        return returnList;
    }

    public static List<String> convertJobTypesToNames(List<JobTypes> jobTypes) {
        List<String> returnList = new ArrayList<String>();
        for (int i = 0; i < jobTypes.size(); i++) {
            JobTypes currJob = jobTypes.get(i);
            if(currJob == JobTypes.ARTS_CREATIVE){
                returnList.add("ARTS_CREATIVE");
            }
            else if(currJob == JobTypes.BABYSITTING){
                returnList.add("BABYSITTING");
            }
            else if(currJob == JobTypes.COOK){
                returnList.add("COOK");
            }
            else if(currJob == JobTypes.HITMAN){
                returnList.add("HITMAN");
            }
            else if(currJob == JobTypes.MAGICIAN){
                returnList.add("MAGICIAN");
            }
            else if(currJob == JobTypes.MOVING){
                returnList.add("MOVING");
            }
            else if(currJob == JobTypes.PETCARE){
                returnList.add("PETCARE");
            }
            else if(currJob == JobTypes.POLITICIAN){
                returnList.add("POLITICIAN");
            }
            else if(currJob == JobTypes.TECH){
                returnList.add("TECH");
            }
            else if(currJob == JobTypes.TUTORING){
                returnList.add("TUTORING");
            }
            else if(currJob == JobTypes.YARDWORK){
                returnList.add("YARDWORK");
            }
            else{
                returnList.add("UNDEFINED");
            }
        }
        return returnList;
    }

    public void checkJob(JobTypes type){
        if(type != JobTypes.UNDEFINED){
            for (int i = 0; i < enumSize; i++) {
                if(preferredJobs.get(i)==type){
                    break;
                }
                else if(preferredJobs.get(i)==JobTypes.UNDEFINED){
                    preferredJobs.set(i, type);
                    break;
                }
            }
        }
    }
    public void uncheckJob(JobTypes type){
        if(type != JobTypes.UNDEFINED){
            for (int i = 0; i < enumSize; i++) {
                if(preferredJobs.get(i)==type){
                    preferredJobs.set(i, JobTypes.UNDEFINED);
                    break;
                }
            }
        }
    }

    public boolean doesPreferred(JobTypes type){
        if(type != JobTypes.UNDEFINED){
            for (int i = 0; i < enumSize; i++) {
                if(preferredJobs.get(i)==type){
                    return true;
                }
            }
            return false;
        }
        else{
            return false;
        }
    }

    public ArrayList<JobTypes>  getPreferredJobs(){
        return preferredJobs;
    }

    public String getEmail(){
        return employeeEmail;
    }
}
