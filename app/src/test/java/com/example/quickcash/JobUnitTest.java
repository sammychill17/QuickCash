package com.example.quickcash;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.quickcash.BusinessLogic.CredentialValidator;
import com.example.quickcash.Objects.Filters.DurationFilter;
import com.example.quickcash.Objects.Filters.FilterHelper;
import com.example.quickcash.Objects.Filters.IFilter;
import com.example.quickcash.Objects.Filters.JobTypeFilter;
import com.example.quickcash.Objects.Filters.SalaryFilter;
import com.example.quickcash.Objects.Filters.TitleFilter;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.JobTypes;

import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JobUnitTest {
    com.example.quickcash.BusinessLogic.CredentialValidator validator;
    @Before
    public void setup() {
        validator = new CredentialValidator();
    }

    @Test
    public void filterByJobTitle (){
        List<IFilter> filters = new ArrayList<>();
        TitleFilter titleFilter = new TitleFilter();
        titleFilter.setValue("oui");
        filters.add(titleFilter);

        FilterHelper helper = new FilterHelper();
        helper.setFilters(filters);
        Set<Job> jobs = helper.run();

        boolean foundOuiJob = false;
        for (Job job : jobs) {
            if (job.getTitle().equals("oui")) {
                foundOuiJob = true;
                break;
            }
        }
        assertTrue(foundOuiJob);
    }
    @Test
    public void filterByDuration (){
        List<IFilter> filters = new ArrayList<>();
        DurationFilter durationFilter = new DurationFilter();
        durationFilter.setValue(5);
        filters.add(durationFilter);

        FilterHelper helper = new FilterHelper();
        helper.setFilters(filters);
        Set<Job> jobs = helper.run();

        boolean foundHitmanJobs = false;
        for (Job job : jobs) {
            Duration jobDuraton = Duration.ofHours(5);
            if (job.getDuration()==jobDuraton) {
                foundHitmanJobs = true;
                break;
            }
        }
        assertTrue(foundHitmanJobs);
    }
    @Test
    public void filterBySalary (){
        List<IFilter> filters = new ArrayList<>();
        SalaryFilter salaryFilter = new SalaryFilter();
        salaryFilter.setValue(500.0);
        filters.add(salaryFilter);

        FilterHelper helper = new FilterHelper();
        helper.setFilters(filters);
        Set<Job> jobs = helper.run();

        boolean foundHitmanJobs = false;
        for (Job job : jobs) {
            if (job.getSalary()==500.0) {
                foundHitmanJobs = true;
                break;
            }
        }
        assertTrue(foundHitmanJobs);
    }
    @Test
    public void filterByJobType (){
        List<IFilter> filters = new ArrayList<>();
        JobTypeFilter jobTypeFilter = new JobTypeFilter();
        jobTypeFilter.setValue(JobTypes.YARDWORK);
        filters.add(jobTypeFilter);

        FilterHelper helper = new FilterHelper();
        helper.setFilters(filters);
        Set<Job> jobs = helper.run();

        boolean foundYardworkJobs = false;
        for (Job job : jobs) {
            if (job.getJobType()==JobTypes.YARDWORK) {
                foundYardworkJobs = true;
                break;
            }
        }
        assertTrue(foundYardworkJobs);
    }
    @Test
    public void filterByJobTitleFail (){
        List<IFilter> filters = new ArrayList<>();
        TitleFilter titleFilter = new TitleFilter();
        titleFilter.setValue("oui");
        filters.add(titleFilter);

        FilterHelper helper = new FilterHelper();
        helper.setFilters(filters);
        Set<Job> jobs = helper.run();

        boolean foundHitmanJob = false;
        for (Job job : jobs) {
            if (job.getTitle().equals("Hitman for hire")) {
                foundHitmanJob = true;
                break;
            }
        }
        assertFalse(foundHitmanJob);
    }
    @Test
    public void filterByDurationFail (){
        List<IFilter> filters = new ArrayList<>();
        DurationFilter durationFilter = new DurationFilter();
        durationFilter.setValue(5);
        filters.add(durationFilter);

        FilterHelper helper = new FilterHelper();
        helper.setFilters(filters);
        Set<Job> jobs = helper.run();

        boolean foundOuiJob = false;
        for (Job job : jobs) {
            Duration jobDuraton = Duration.ofHours(0);
            if (job.getDuration()==jobDuraton) {
                foundOuiJob = true;
                break;
            }
        }
        assertFalse(foundOuiJob);
    }
    @Test
    public void filterBySalaryFail (){
        List<IFilter> filters = new ArrayList<>();
        SalaryFilter salaryFilter = new SalaryFilter();
        salaryFilter.setValue(500.0);
        filters.add(salaryFilter);

        FilterHelper helper = new FilterHelper();
        helper.setFilters(filters);
        Set<Job> jobs = helper.run();

        boolean foundOuiJob = false;
        for (Job job : jobs) {
            if (job.getSalary()==0.0) {
                foundOuiJob = true;
                break;
            }
        }
        assertFalse(foundOuiJob);
    }
    @Test
    public void filterByJobTypeFail (){
        List<IFilter> filters = new ArrayList<>();
        JobTypeFilter jobTypeFilter = new JobTypeFilter();
        jobTypeFilter.setValue(JobTypes.YARDWORK);
        filters.add(jobTypeFilter);

        FilterHelper helper = new FilterHelper();
        helper.setFilters(filters);
        Set<Job> jobs = helper.run();

        boolean foundArtsCreativeJob = false;
        for (Job job : jobs) {
            if (job.getJobType()==JobTypes.ARTS_CREATIVE) {
                foundArtsCreativeJob = true;
                break;
            }
        }
        assertFalse(foundArtsCreativeJob);
    }
}
