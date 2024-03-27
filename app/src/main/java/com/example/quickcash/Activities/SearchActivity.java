package com.example.quickcash.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.quickcash.Activities.Adapters.SearchItemAdapter;
import com.example.quickcash.BusinessLogic.SanitizeEmail;
import com.example.quickcash.FirebaseStuff.FirebasePreferredJobsHelper;
import com.example.quickcash.Objects.Filters.FilterHelper;
import com.example.quickcash.Objects.Filters.IFilter;
import com.example.quickcash.Objects.Filters.TitleFilter;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.JobTypes;
import com.example.quickcash.Objects.PreferredJobs;
import com.example.quickcash.R;

import com.example.quickcash.ui.map.MapFragment;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/*
* This activity is designed to allow the employee to search for jobs in their area by querying the database.
*
 */
public class SearchActivity extends AppCompatActivity {
    private ImageButton filterButton;
    private RecyclerView recyclerView;
    private EditText searchBar;
    private Button mapsButton;
    List<Job> jobs = new ArrayList<>();
    List<IFilter> filters = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("Logcat-chan", "Meow~");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        FirebasePreferredJobsHelper helper = new FirebasePreferredJobsHelper();
        SharedPreferences sharedPrefs = this.getSharedPreferences("session_login", MODE_PRIVATE);
        String employeeEmail = sharedPrefs.getString("email", "");
        PreferredJobs currPref = new PreferredJobs(employeeEmail);
        helper.retrievePreferredJobs(employeeEmail, preferredJobs -> {
            ArrayList<JobTypes> list = new ArrayList<JobTypes>();
            list.add(JobTypes.ARTS_CREATIVE);
            list.add(JobTypes.BABYSITTING);
            list.add(JobTypes.COOK);
            list.add(JobTypes.HITMAN);
            list.add(JobTypes.MAGICIAN);
            list.add(JobTypes.MOVING);
            list.add(JobTypes.PETCARE);
            list.add(JobTypes.POLITICIAN);
            list.add(JobTypes.TECH);
            list.add(JobTypes.TUTORING);
            list.add(JobTypes.YARDWORK);

            for (int i = 0; i < 11; i++) {
                JobTypes jobType = list.get(i);
                if (preferredJobs.contains(jobType.name())) {
                    currPref.checkJob(jobType);
                }
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.searchResultsRecycler);
        SearchItemAdapter adapter = new SearchItemAdapter(jobs, getApplicationContext(), currPref, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.getAdapter().notifyDataSetChanged();


        searchBar = (EditText) findViewById(R.id.searchBar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("SearchActivity", "You typed: " + s);

                applyFiltersAndSearch();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //
            }
        });

        filterButton = findViewById(R.id.filterButton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterActivity filterActivity = new FilterActivity();
                filterActivity.setCallback(new FilterActivity.FilterCompleteCallback() {
                    @Override
                    public void onResult(List<IFilter> newFilters) {
                        filters = newFilters;

                        applyFiltersAndSearch();
                        filterActivity.dismiss();
                    }
                });
                filterActivity.show(getSupportFragmentManager(), "Filter fragment");
            }
        });

        mapsButton = findViewById(R.id.mapButton);
        mapsButton.setOnClickListener(v -> {
            SearchItemAdapter itemAdapter = (SearchItemAdapter) recyclerView.getAdapter();
            ArrayList<Job> jobs = (ArrayList<Job>) itemAdapter.getList();
            MapFragment mapFragment = new MapFragment(jobs);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.map, mapFragment)
                    .commit();
        });

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void applyFiltersAndSearch() {
        jobs.clear();
        recyclerView.getAdapter().notifyDataSetChanged();

        List<IFilter> filters1 = new ArrayList<>(filters);
        if (searchBar.getText().length() != 0) {
            TitleFilter titleFilter = new TitleFilter();
            titleFilter.setValue(searchBar.getText().toString());
            filters1.add(titleFilter);
        }

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
        helper.setFilters(filters1);
        helper.run();
    }

}