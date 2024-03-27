package com.example.quickcash;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import com.example.quickcash.BusinessLogic.CredentialValidator;
import com.example.quickcash.Objects.Filters.DurationFilter;
import com.example.quickcash.Objects.Filters.FilterHelper;
import com.example.quickcash.Objects.Filters.IFilter;
import com.example.quickcash.Objects.Filters.JobTypeFilter;
import com.example.quickcash.Objects.Filters.SalaryFilter;
import com.example.quickcash.Objects.Filters.TitleFilter;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.JobTypes;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JobUnitTest {
    com.example.quickcash.BusinessLogic.CredentialValidator validator;
    FilterHelper helper;
    @Before
    public void setup() {
        validator = new CredentialValidator();
        helper = mock(FilterHelper.class);
    }

    @Test
    public void filterByJobTitle (){
        List<IFilter> filters = new ArrayList<>();
        TitleFilter titleFilter = new TitleFilter();
        titleFilter.setValue("oui");
        filters.add(titleFilter);

        FilterHelper.FilterHelperCallback callback = new FilterHelper.FilterHelperCallback() {
            @Override
            public void onResult(Set<Job> searchResult) {
                boolean foundOuiJob = false;
                for (Job job : searchResult) {
                    if (job.getTitle().equals("oui")) {
                        foundOuiJob = true;
                        break;
                    }
                }
                assertTrue(foundOuiJob);
            }
        };
        helper.setCallback(callback);
        helper.setFilters(filters);
        helper.run();
    }
    @Test
    public void filterByDuration (){
        List<IFilter> filters = new ArrayList<>();
        DurationFilter durationFilter = new DurationFilter();
        durationFilter.setValue(5);
        filters.add(durationFilter);

        helper.setCallback(new FilterHelper.FilterHelperCallback() {
            @Override
            public void onResult(Set<Job> jobs) {
                boolean foundHitmanJobs = false;
                for (Job job : jobs) {
                    Duration jobDuraton = Duration.ofHours(5);
                    if (job.getDuration()==jobDuraton.toHours()) {
                        foundHitmanJobs = true;
                        break;
                    }
                }
                assertTrue(foundHitmanJobs);
            }
        });
        helper.setFilters(filters);
        helper.run();
    }
    @Test
    public void filterBySalary (){
        List<IFilter> filters = new ArrayList<>();
        SalaryFilter salaryFilter = new SalaryFilter();
        salaryFilter.setValue(500.0);
        filters.add(salaryFilter);

        helper.setFilters(filters);
        helper.setCallback(new FilterHelper.FilterHelperCallback() {
            @Override
            public void onResult(Set<Job> jobs) {
                boolean foundHitmanJobs = false;
                for (Job job : jobs) {
                    if (job.getSalary()==500.0) {
                        foundHitmanJobs = true;
                        break;
                    }
                }
                assertTrue(foundHitmanJobs);
            }
        });
        helper.run();
    }
    @Test
    public void filterByJobType (){
        List<IFilter> filters = new ArrayList<>();
        JobTypeFilter jobTypeFilter = new JobTypeFilter();
        jobTypeFilter.setValue(JobTypes.YARDWORK);
        filters.add(jobTypeFilter);

        helper.setFilters(filters);
        helper.setCallback(new FilterHelper.FilterHelperCallback() {
            @Override
            public void onResult(Set<Job> jobs) {
                boolean foundYardworkJobs = false;
                for (Job job : jobs) {
                    if (job.getJobType()==JobTypes.YARDWORK) {
                        foundYardworkJobs = true;
                        break;
                    }
                }
                assertTrue(foundYardworkJobs);
            }
        });
        helper.run();
    }
    @Test
    public void filterByJobTitleFail (){
        List<IFilter> filters = new ArrayList<>();
        TitleFilter titleFilter = new TitleFilter();
        titleFilter.setValue("oui");
        filters.add(titleFilter);

        helper.setFilters(filters);
        helper.setCallback(new FilterHelper.FilterHelperCallback() {
            @Override
            public void onResult(Set<Job> jobs) {
                boolean foundHitmanJob = false;
                for (Job job : jobs) {
                    if (job.getTitle().equals("Hitman for hire")) {
                        foundHitmanJob = true;
                        break;
                    }
                }
                assertFalse(foundHitmanJob);
            }
        });
        helper.run();
    }
    @Test
    public void filterByDurationFail (){
        List<IFilter> filters = new ArrayList<>();
        DurationFilter durationFilter = new DurationFilter();
        durationFilter.setValue(5);
        filters.add(durationFilter);

        helper.setFilters(filters);
        helper.setCallback(new FilterHelper.FilterHelperCallback() {
            @Override
            public void onResult(Set<Job> jobs) {
                boolean foundOuiJob = false;
                for (Job job : jobs) {
                    Duration jobDuraton = Duration.ofHours(0);
                    if (job.getDuration()==jobDuraton.toHours()) {
                        foundOuiJob = true;
                        break;
                    }
                }
                assertFalse(foundOuiJob);
            }
        });
        helper.run();
    }
    @Test
    public void filterBySalaryFail (){
        List<IFilter> filters = new ArrayList<>();
        SalaryFilter salaryFilter = new SalaryFilter();
        salaryFilter.setValue(500.0);
        filters.add(salaryFilter);

        helper.setFilters(filters);
        helper.setCallback(new FilterHelper.FilterHelperCallback() {
            @Override
            public void onResult(Set<Job> jobs) {
                boolean foundOuiJob = false;
                for (Job job : jobs) {
                    if (job.getSalary()==0.0) {
                        foundOuiJob = true;
                        break;
                    }
                }
                assertFalse(foundOuiJob);
            }
        });
        helper.run();
    }
    @Test
    public void filterByJobTypeFail (){
        List<IFilter> filters = new ArrayList<>();
        JobTypeFilter jobTypeFilter = new JobTypeFilter();
        jobTypeFilter.setValue(JobTypes.YARDWORK);
        filters.add(jobTypeFilter);

        helper.setFilters(filters);
        helper.setCallback(new FilterHelper.FilterHelperCallback() {
            @Override
            public void onResult(Set<Job> jobs) {
                boolean foundArtsCreativeJob = false;
                for (Job job : jobs) {
                    if (job.getJobType()==JobTypes.ARTS_CREATIVE) {
                        foundArtsCreativeJob = true;
                        break;
                    }
                }
                assertFalse(foundArtsCreativeJob);
            }
        });
        helper.run();
    }
}
