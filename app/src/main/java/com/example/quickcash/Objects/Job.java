package com.example.quickcash.Objects;

import android.location.Location;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

/*
* This class is designed to hold the required information on the specific jobs for use in the QuickCash app.
 */
public class Job implements Serializable {

    private String key;
    private String title;
    private String description;
    private JobTypes jobType;
    private double salary;
    private transient Duration duration;
    private Date date;
    private long epoch;
    private UserLocation location;
    private boolean isAssigned;
    private boolean isCompleted;
    private String assignedToEmail;
    private String employerEmail;
    private double latitude;
    private double longitude;

    /*
    Constructor --
     */
    public Job(String title,
               String description,
               JobTypes jobType,
               double salary,
               Duration duration,
               String employerEmail,
               Date date,
               double latitude,
               double longitude) {
        this.title = title;
        this.description = description;
        this.jobType = jobType;
        this.salary = salary;
        this.duration = duration;
        this.employerEmail = employerEmail;
        this.date = date;
        /*
        default value is false
         */
        this.isAssigned = false;
        this.isCompleted = false;
        /*
        empty string initially
         */
        this.assignedToEmail = "";
        this.latitude = latitude;
        this.longitude = longitude;
        this.epoch = date.getTime();
    }

    /*
    * default constructor for Job item with no parameters.
     */
    public Job(){
        this.title = "";
        this.description = "";
        this.jobType = JobTypes.UNDEFINED;
        this.salary = 0;
        this.duration = Duration.ofHours(0);
        this.employerEmail = "";
        this.date = Date.from(Instant.now());
        /*
        default value is false
         */
        this.isAssigned = false;
        this.isCompleted = false;
        /*
        empty string initially
         */
        this.assignedToEmail = "";
        this.latitude = 0;
        this.longitude = 0;
        this.epoch = 0;
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
    public JobTypes getJobType() {
        return jobType;
    }

    public void setJobType(JobTypes jobType) {
        this.jobType = jobType;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public long getDuration() {
        return duration.toHours();
    }


    public void setDuration(long hours) {
        this.duration = Duration.ofHours(hours);
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

    public void setKey(String key) {this.key = key;}
    public String getKey(){
        return  key;
    }

    public Date getDate(){
        return date;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setEpoch(long epoch){
        this.epoch = epoch;
    }

    public long getEpoch(){
        return epoch;
    }
}
