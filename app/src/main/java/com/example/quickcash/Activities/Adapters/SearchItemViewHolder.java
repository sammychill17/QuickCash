package com.example.quickcash.Activities.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;

/**
 * The view holder for each search item
 * You need this class for the SearchItemAdapter
 */
public class SearchItemViewHolder extends RecyclerView.ViewHolder{
    /**
     * The Title label.
     */
    TextView titleView;
    /**
     * The Description label.
     */
    TextView descView;
    /**
     * The leading ImageView for job icon.
     */
    ImageView picView;
    /**
     * The "View more" button (currently a ">" label).
     */
    TextView viewMoreView;
    /**
     * The entire job item as a whole.
     */
    View itemView;

    /**
     * Instantiates a new Search item view holder.
     *
     * @param itemView the item view to instantiate from
     */
    public SearchItemViewHolder(@NonNull View itemView) {
        super(itemView);

        titleView = (TextView) itemView.findViewById(R.id.jobItem_title);
        descView = (TextView) itemView.findViewById(R.id.jobItem_description);
        picView = (ImageView) itemView.findViewById(R.id.jobItem_leadingIcon);
        viewMoreView = (TextView) itemView.findViewById(R.id.jobItem_viewMore);
        this.itemView = itemView;
    }
}
