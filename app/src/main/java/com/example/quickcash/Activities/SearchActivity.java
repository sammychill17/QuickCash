package com.example.quickcash.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.quickcash.Activities.Adapters.SearchItemAdapter;
import com.example.quickcash.Objects.Filters.FilterHelper;
import com.example.quickcash.Objects.Filters.IFilter;
import com.example.quickcash.Objects.Filters.TitleFilter;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.UserLocation;
import com.example.quickcash.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SearchActivity extends AppCompatActivity {
    private static final int UNDEFINED_VALUE = -999;
    private ImageButton filterButton;
    private RecyclerView recyclerView;
    private EditText searchBar;
    private List<Job> jobs = new ArrayList<>();
    private List<IFilter> filters = new ArrayList<>();
    private UserLocation location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("Logcat-chan", "Meow~");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = (RecyclerView) findViewById(R.id.searchResultsRecycler);
        SearchItemAdapter adapter = new SearchItemAdapter(jobs, getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        recyclerView.getAdapter().notifyDataSetChanged();

        double currLong = getIntent().getDoubleExtra("currLong", UNDEFINED_VALUE);
        double currLat = getIntent().getDoubleExtra("currLat", UNDEFINED_VALUE);
        if (currLat != UNDEFINED_VALUE && currLong != UNDEFINED_VALUE) {
            location = new UserLocation();
            location.setLatitude(currLat);
            location.setLongitude(currLong);
        }

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
                if (location != null) {
                    filterActivity.setUserLocation(location);
                }
                filterActivity.setCallback(new FilterActivity.FilterCompleteCallback() {
                    @Override
                    public void onResult(List<IFilter> newFilters) {
                        filters = newFilters;

                        filterActivity.dismiss();

                        applyFiltersAndSearch();
                    }
                });
                filterActivity.show(getSupportFragmentManager(), "Filter fragment");
            }
        });
    }

    private void applyFiltersAndSearch() {
        int oldJobSize = jobs.size();
        jobs.clear();
        recyclerView.getAdapter().notifyItemRangeRemoved(0, oldJobSize);

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