package com.example.quickcash.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;


import com.example.quickcash.Activities.Adapters.MyEmployeesAdapter;
import com.example.quickcash.Activities.Adapters.SearchItemAdapter;
import com.example.quickcash.FirebaseStuff.JamesDBHelper;
import com.example.quickcash.Objects.Employee;
import com.example.quickcash.R;

import java.util.ArrayList;

public class MyEmployeesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ArrayList<Employee> employees;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_employees);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sp = getSharedPreferences("session_login", MODE_PRIVATE);
        String userEmail = sp.getString("email", "");
        JamesDBHelper jamesDBHelper = new JamesDBHelper(userEmail);
        employees = jamesDBHelper.getReturnList();

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