package com.example.quickcash.Objects;

import java.time.Duration;
import java.util.Random;

public class Job {

    private String key;
    private String title;
    private String description;
    private JobTypes jobType;
    private double salary;
    private Duration duration;
    private boolean isAssigned;
    private boolean isCompleted;
    private String assignedToEmail;
    private String employerEmail;

    /*
    Constructor --
     */
    public Job(String title, String description, JobTypes jobType, double salary, String duration, String employerEmail) {
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
    public Job() {}

    public static Job getRandomJob(Random random) {
        JobTypes jobTypes = JobTypes.values()[random.nextInt(11)];
        Duration duration = Duration.ZERO.plusMillis(random.nextInt(999999999));
        Job job = new Job(getRandomGibberish(random), getRandomGibberish(random), jobTypes, random.nextDouble(), duration.toString(), "Loki360@gmail.com");
        return job;
    }

    private static String getRandomGibberish(Random random) {
        StringBuilder builder = new StringBuilder();
        try {
            builder.append(Character.getName(random.nextInt(Character.FINAL_QUOTE_PUNCTUATION)));
        }  catch (Exception e) {
            for (int i = 0; i < 16; i++) {
                String s = e.toString();
                builder.append(s.charAt(random.nextInt(s.length())));
            }
        }
        builder.append(random.nextInt(999999));
        return builder.toString();
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Duration getDuration() {
        return duration;
    }

//    public void setDuration(String duration) {
//        this.duration = Duration.parse(duration);
//    }

    public void setDuration(Long duration) {
        this.duration = Duration.ofHours(duration);
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

    public String getKey(){
        return  key;
    }

    //TODO: Make a Job Firebase Helper Class
}
