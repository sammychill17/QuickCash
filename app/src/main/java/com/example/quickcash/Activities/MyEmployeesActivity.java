package com.example.quickcash.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.example.quickcash.Activities.Adapters.MyEmployeesAdapter;
import com.example.quickcash.FirebaseStuff.ApplicantDBHelper;
import com.example.quickcash.Objects.Employee;
import com.example.quickcash.R;

import java.util.ArrayList;
import java.util.List;

public class MyEmployeesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<Employee> employees = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_employees);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Employees");

        SharedPreferences sp = getSharedPreferences("session_login", MODE_PRIVATE);
        String userEmail = sp.getString("email", "");
        ApplicantDBHelper applicantDBHelper = new ApplicantDBHelper(userEmail);

        applicantDBHelper.runHelper(new ApplicantDBHelper.DatabaseScroungerCallback(){

            @Override
            public void onResult(List<Employee> list){
                recyclerView.getAdapter().notifyItemRangeRemoved(0, employees.size());
                employees.clear();
                employees.addAll(list);
                for (int i = 0; i < employees.size(); i++) {
                    if(employees.get(i)==null){
                        employees.remove(i);
                        i--;
                    }
                }
                recyclerView.getAdapter().notifyItemRangeInserted(0, employees.size());

                if(employees.isEmpty()){
                    TextView emptyTextView = findViewById(R.id.emptyListTextView);
                    emptyTextView.setVisibility(View.VISIBLE);
                }
                else{
                    TextView emptyTextView = findViewById(R.id.emptyListTextView);
                    emptyTextView.setVisibility(View.INVISIBLE);
                }
            }

        });

        employees.add(new Employee("Loading", "Loading", "Loading", "Loading"));

        recyclerView = (RecyclerView) findViewById(R.id.myEmployeeList);
        MyEmployeesAdapter adapter = new MyEmployeesAdapter(employees, getApplicationContext(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}