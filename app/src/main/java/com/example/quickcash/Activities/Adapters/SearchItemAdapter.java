package com.example.quickcash.Activities.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.Objects.Job;
import com.example.quickcash.R;

import java.util.Collections;
import java.util.List;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemViewHolder> {
    List<Job> list = Collections.emptyList();
    Context context;

    public SearchItemAdapter(List<Job> jobs, Context context) {
        list = jobs;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the layout
        View jobItem = inflater.inflate(R.layout.layout_jobitem, parent, false);

//        jobItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Recy
//            }
//        });

        SearchItemViewHolder viewHolder = new SearchItemViewHolder(jobItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemViewHolder holder, int position) {
        final int index = holder.getAdapterPosition();
        Job job = list.get(index);
        holder.titleView.setText(job.getTitle());
        holder.descView.setText(job.getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
