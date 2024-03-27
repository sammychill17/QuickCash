package com.example.quickcash;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.quickcash.BusinessLogic.CredentialValidator;
import com.example.quickcash.Objects.JobApplicants;

import org.junit.Before;
import org.junit.Test;


public class JobApplicantsUnitTests {
    JobApplicants jobApplicants;


    @Before
    public void setup() {
        jobApplicants = new JobApplicants("hi");
    }
    @Test
    public void addApplicant_addsCorrectly() {
        jobApplicants.addApplicant("homeless@canadian.streets");
        assertTrue(jobApplicants.getApplicants().contains("homeless@canadian.streets"));
    }

    @Test
    public void removeApplicant_removesCorrectly() {
        jobApplicants.addApplicant("gaga@radio.com");
        jobApplicants.removeApplicant("gaga@radio.com");
        assertFalse(jobApplicants.getApplicants().contains("gaga@radio.com"));
    }

    @Test
    public void ifContainsApplicant_checksCorrectly() {
        jobApplicants.addApplicant("manowar@war.com");
        assertTrue(jobApplicants.ifContainsApplicant("manowar@war.com"));
        assertFalse(jobApplicants.ifContainsApplicant("nonexistent@idontexist.com"));
    }

    @Test
    public void clearAllApplicants_clearsCorrectly() {
        jobApplicants.addApplicant("onclejazz@terabithia.com");
        jobApplicants.addApplicant("boogles@oogles.com");
        jobApplicants.clearAllApplicants();
        assertTrue(jobApplicants.getApplicants().isEmpty());
    }
}
