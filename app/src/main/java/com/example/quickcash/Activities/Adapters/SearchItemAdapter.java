package com.example.quickcash.Activities.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.Activities.JobApplyActivity;
import com.example.quickcash.Activities.SearchActivity;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.R;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemViewHolder> implements Serializable {
    List<Job> list = Collections.emptyList();
    Context context;

    SearchActivity currActivity;


    public SearchItemAdapter(List<Job> jobs, Context context, SearchActivity currActivity) {
        list = jobs;
        this.context = context;
        this.currActivity = currActivity;
    }

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context thisContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(thisContext);

        // Inflate the layout
        View jobItem = inflater.inflate(R.layout.layout_jobitem, parent, false);

        return new SearchItemViewHolder(jobItem);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemViewHolder holder, int position) {
        final int index = holder.getAdapterPosition();
        Job job = list.get(index);
        holder.titleView.setText(job.getTitle());
        holder.descView.setText(job.getDescription());
        holder.viewMoreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(currActivity, JobApplyActivity.class);
                intent.putExtra("job", job);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                currActivity.startActivity(intent);
            }
        });

        switch (job.getJobType()) {
            case COOK:
                holder.picView.setImageResource(R.drawable.icon_cook);
                break;
            case TECH:
                holder.picView.setImageResource(R.drawable.icon_tech);
                break;
            case HITMAN:
                holder.picView.setImageResource(R.drawable.icon_hitman);
                break;
            case MOVING:
                holder.picView.setImageResource(R.drawable.icon_moving);
                break;
            case PETCARE:
                holder.picView.setImageResource(R.drawable.icon_petcare);
                break;
            case MAGICIAN:
                holder.picView.setImageResource(R.drawable.icon_magician);
                break;
            case TUTORING:
                holder.picView.setImageResource(R.drawable.icon_tutoring);
                break;
            case YARDWORK:
                holder.picView.setImageResource(R.drawable.icon_yardwork);
                break;
            case POLITICIAN:
                holder.picView.setImageResource(R.drawable.icon_politician);
                break;
            case BABYSITTING:
                holder.picView.setImageResource(R.drawable.icon_babysitting);
                break;
            case ARTS_CREATIVE:
                holder.picView.setImageResource(R.drawable.icon_arts_creative);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
