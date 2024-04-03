package com.example.quickcash.ui.employeeJobHistory;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.quickcash.FirebaseStuff.RatingDBHelper;
import com.example.quickcash.Objects.Rating;
import com.example.quickcash.Objects.Review;
import com.example.quickcash.R;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EmployeeRatingsHistoryFragment extends Fragment {
    private ListView ratingsListView;
    private SimpleAdapter ratingsAdapter;
    private List<Map<String, String>> ratingsList;

    /* Initializes the view for the ratings history screen */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ratings_history, container, false);
        ratingsListView = view.findViewById(R.id.ratingsListView);
        ratingsList = new ArrayList<>();

        /* Set up the back button to return to the previous screen */
        final Button backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        /* Load ratings from the database */
        loadRatings();
        return view;
    }

    /* Loads the ratings from the database and populates the ListView */
    private void loadRatings() {
        /* Retrieve the logged-in user's email from shared preferences */
        SharedPreferences sp = getActivity().getSharedPreferences("session_login", Context.MODE_PRIVATE);
        String loggedInUserEmail = sp.getString("email", "");

        /*
        uses the RatingDBHelper to get ratings for the logged-in employee
        */
        new RatingDBHelper().getRatingsByEmployee(loggedInUserEmail, new RatingDBHelper.onRatingReceivedCallback() {
            /*
            handles the receipts of ratings data
            */
            @Override
            public void onRatingReceived(Rating rating) {
                if (rating != null && rating.getReviewList() != null) {
                    double totalStars = 0;
                    /*
                    iterates over each review to populate the ListView and thus, calculates the total stars
                    */
                    for (Review review : rating.getReviewList()) {
                        Map<String, String> ratingMap = new HashMap<>();
                        ratingMap.put("description", review.getDescription());
                        ratingMap.put("ratingStars", String.valueOf(review.getRatingStars()));
                        ratingsList.add(ratingMap);
                        totalStars += review.getRatingStars();
                    }

                    /*
                    this calculates the average rating and update the text view,
                    better approach than querying from firebase
                    (querying from firebase is the easy way out, just kidding!)
                    */
                    double averageRating = totalStars / rating.getReviewList().size();
                    TextView averageRatingTextView = getView().findViewById(R.id.averageRatingTextView);
                    averageRatingTextView.setText(String.format(Locale.getDefault(), "Average Rating: %.2f", averageRating));

                    /*
                    sets up the adapter to display the ratings and descriptions in the list view
                    */
                    String[] from = {"description", "ratingStars"};
                    int[] to = {R.id.ratingDescription, R.id.ratingStars};

                    ratingsAdapter = new SimpleAdapter(getContext(), ratingsList, R.layout.rating_item, from, to);
                    ratingsListView.setAdapter(ratingsAdapter);
                }
            }
            @Override
            public void onError(DatabaseError error) {
            }
        });
    }
}
