package com.example.quickcash.Objects;

import java.util.ArrayList;
import java.util.List;

public class JobApplicants {

    private String key;
    private List<String> applicants;

    public JobApplicants(String key){
        this.key = key;
        this.applicants = new ArrayList<>();
    }
    public JobApplicants(int key, List<String> applicants){
        this.applicants = applicants;
    }

    public String getKey() {
        return key;
    }
    public List<String> getApplicants() {
        return applicants;
    }

    public void addApplicant(String email){
        applicants.add(email);
    }

    public void removeApplicant(String email){
        for(int i = 0; i < applicants.size(); i++) {
            if(email.equals(applicants.get(i))){
                applicants.remove(i);
                break;
            }
        }
    }

    public boolean ifContainsApplicant(String email){
        return applicants.contains(email);
    }

    public void clearAllApplicants(){
        applicants.clear();
    }
}