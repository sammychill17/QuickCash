package com.example.quickcash.Activities.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.Activities.MyEmployeesActivity;
import com.example.quickcash.Activities.SearchActivity;
import com.example.quickcash.Objects.Employee;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.PreferredJobs;
import com.example.quickcash.R;
import com.example.quickcash.ui.employerApplicantPage.EmployerApplicantPageFragment;
import com.example.quickcash.ui.employerJobApplicants.JobApplicantsHolder;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class MyEmployeesAdapter extends RecyclerView.Adapter<MyEmployeesViewHolder> implements Serializable {

    private List<Employee> list = Collections.emptyList();

    private Context context;

    private MyEmployeesActivity currActivity;

    @NonNull
    @Override
    public MyEmployeesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context thisContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(thisContext);

        View employeeItem = inflater.inflate(R.layout.layout_my_employee_item, parent, false);

        return new MyEmployeesViewHolder(employeeItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyEmployeesViewHolder holder, int position) {
        holder.title.setText(list.get(position).getName());

        holder.payButton.setOnClickListener(v -> {

        });

        holder.rateButton.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<Employee> getList(){
        return list;
    }
}
