package com.example.quickcash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.quickcash.BusinessLogic.CredentialValidator;
import com.example.quickcash.Objects.JobApplicants;
import com.example.quickcash.Objects.JobTypes;
import com.example.quickcash.Objects.PreferredJobs;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class PreferredJobsUnitTests {
    PreferredJobs preferredJobs;
    @Before
    public void setup() {
        preferredJobs = new PreferredJobs("macgrubber@yolo.com");
    }
    @Test
    public void checkJobAddsJobCorrectly() {
        preferredJobs.checkJob(JobTypes.HITMAN);
        assertTrue("Job should be added to the preferred jobs accordingly.",
                preferredJobs.getPreferredJobs().contains(JobTypes.HITMAN));
    }

    @Test
    public void uncheckJobRemovesJobCorrectly() {
        preferredJobs.checkJob(JobTypes.HITMAN);
        preferredJobs.checkJob(JobTypes.MAGICIAN);
        preferredJobs.uncheckJob(JobTypes.HITMAN);
        assertFalse("Job should be removed from the preferred jobs",
                preferredJobs.getPreferredJobs().contains(JobTypes.HITMAN));
    }

    @Test
    public void doesPreferredReturnsTrueForPreferredJob() {
        preferredJobs.checkJob(JobTypes.MOVING);
        assertTrue("Should return true for checked preferred job",
                preferredJobs.doesPreferred(JobTypes.MOVING));
    }

    @Test
    public void doesPreferredReturnsFalseForNotPreferredJob() {
        assertFalse("Should return false for job which is not checked for preference",
                preferredJobs.doesPreferred(JobTypes.YARDWORK));
    }
    @Test
    public void convertNamesToJobTypes_ConvertsCorrectly() {
        List<String> names = Arrays.asList("YARDWORK", "MOVING");
        List<JobTypes> expectedJobTypes = Arrays.asList(JobTypes.YARDWORK, JobTypes.MOVING);

        List<JobTypes> resultJobTypes = PreferredJobs.convertNamesToJobTypes(names);

        assertEquals(expectedJobTypes, resultJobTypes);
    }

    @Test
    public void convertJobTypesToNames_ConvertsCorrectly() {
        List<JobTypes> jobTypes = Arrays.asList(JobTypes.YARDWORK, JobTypes.MOVING);
        List<String> expectedNames = Arrays.asList("YARDWORK", "MOVING");

        List<String> resultNames = PreferredJobs.convertJobTypesToNames(jobTypes);

        assertEquals(expectedNames, resultNames);
    }
}
