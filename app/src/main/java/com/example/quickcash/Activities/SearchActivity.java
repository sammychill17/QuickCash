package com.example.quickcash.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.quickcash.Activities.Adapters.SearchItemAdapter;
import com.example.quickcash.Objects.Filters.FilterHelper;
import com.example.quickcash.Objects.Filters.IFilter;
import com.example.quickcash.Objects.Filters.TitleFilter;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Logcat-chan", "Meow~");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        List<Job> jobs = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.searchResultsRecycler);
        SearchItemAdapter adapter = new SearchItemAdapter(jobs, getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.getAdapter().notifyDataSetChanged();
//        Random random = new Random();
//        for (int i = 0; i < 50; i++) {
//            jobs.add(Job.getRandomJob(random));
//        }
        List<IFilter> filters = new ArrayList<>();
        TitleFilter titleFilter = new TitleFilter();
        titleFilter.setValue("oui");
        filters.add(titleFilter);

        FilterHelper helper = new FilterHelper();
        FilterHelper.FilterHelperCallback callback = new FilterHelper.FilterHelperCallback() {
            @Override
            public void onResult(Set<Job> searchResult) {
                for (Job job : searchResult) {
                    jobs.add(job);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        };
        helper.setCallback(callback);
        helper.setFilters(filters);
        helper.run();
    }
}