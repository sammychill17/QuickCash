package com.example.quickcash.Activities.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;

public class SearchItemViewHolder extends RecyclerView.ViewHolder{
    TextView titleView;
    TextView descView;
    ImageView picView;
    TextView viewMoreView;
    View itemView;

    public SearchItemViewHolder(@NonNull View itemView) {
        super(itemView);

        titleView = (TextView) itemView.findViewById(R.id.jobItem_title);
        descView = (TextView) itemView.findViewById(R.id.jobItem_description);
        picView = (ImageView) itemView.findViewById(R.id.jobItem_leadingIcon);
        viewMoreView = (TextView) itemView.findViewById(R.id.jobItem_viewMore);
        this.itemView = itemView;
    }
}
