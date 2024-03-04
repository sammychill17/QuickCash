package com.example.quickcash.Objects;

import java.time.Duration;

public class Job {

    private int key;
    private String title;
    private String description;
    private TypesOfJobs jobType;
    private double salary;
    private Duration duration;
    private boolean isAssigned;
    private boolean isCompleted;
    private String assignedToEmail;
    private String employerEmail;

    /*
    Constructor --
     */
    public Job(String title, String description, TypesOfJobs jobType, double salary, String duration, String employerEmail) {
        this.title = title;
        this.description = description;
        this.jobType = jobType;
        this.salary = salary;
        this.duration = Duration.parse(duration);
        this.employerEmail = employerEmail;
        /*
        default value is false
         */
        this.isAssigned = false;
        this.isCompleted = false;
        /*
        empty string initially
         */
        this.assignedToEmail = "";
    }

    /*
    getter and setter methods below---
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*
    the enumpire strikes back/j
     */
    public TypesOfJobs getJobType() {
        return jobType;
    }

    public void setJobType(TypesOfJobs jobType) {
        this.jobType = jobType;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = Duration.parse(duration);
    }

    public boolean isAssigned() {
        return isAssigned;
    }

    public String getAssigneeEmail() {
        return assignedToEmail;
    }

    public void assignJob(String assignedToEmail) {
        this.isAssigned = true;
        if (assignedToEmail.equals("")) {
            clearAssigned();
        }
        this.assignedToEmail = assignedToEmail;
    }

    public void clearAssigned() {
        this.isAssigned = false;
        this.assignedToEmail = "";
    }

    public String getEmployer() {
        return employerEmail;
    }

    public void setEmployerEmail(String employerEmail) {
        this.employerEmail = employerEmail;
    }

    //TODO: Make a Job Firebase Helper Class
}
