package com.example.quickcash.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

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

    List<Job> jobs = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Logcat-chan", "Meow~");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.searchResultsRecycler);
        SearchItemAdapter adapter = new SearchItemAdapter(jobs, getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.getAdapter().notifyDataSetChanged();
//        Random random = new Random();
//        for (int i = 0; i < 50; i++) {
//            jobs.add(Job.getRandomJob(random));
//        }


        EditText searchBar = (EditText) findViewById(R.id.searchBar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("SearchActivity", "You typed: " + s);
                jobs.clear();
                recyclerView.getAdapter().notifyDataSetChanged();
                List<IFilter> filters = new ArrayList<>();
                TitleFilter titleFilter = new TitleFilter();
                titleFilter.setValue(s);
                filters.add(titleFilter);

                FilterHelper helper = new FilterHelper();
                FilterHelper.FilterHelperCallback callback = new FilterHelper.FilterHelperCallback() {
                    @Override
                    public void onResult(Set<Job> searchResult) {
                        for (Job job : searchResult) {
                            Log.d("SearchActivity", "I see " + job.getTitle());
                            jobs.add(job);
                            recyclerView.getAdapter().notifyItemChanged(jobs.size() - 2, job);
                        }
                    }
                };
                helper.setCallback(callback);
                helper.setFilters(filters);
                helper.run();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //
            }
        });
    }
}