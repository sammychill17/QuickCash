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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.quickcash.Objects.Rating;
import com.example.quickcash.Objects.Review;
import com.example.quickcash.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeRatingsHistoryFragment extends Fragment {
    private ListView ratingsListView;
    private List<Map<String, String>> ratingsList = new ArrayList<>();
    private SimpleAdapter ratingsAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ratings_history, container, false);
        ratingsListView = view.findViewById(R.id.ratingsListView);
        final Button backButton = view.findViewById(R.id.backButton);
        /* Set up the back button to return to the previous screen */
        backButton.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        /* Load ratings from the database */
        loadRatings();
        return view;
    }
    /* For displaying ratings and reviews */
    private void displayRatings(Rating rating) {
        /* Clearing previous data to ensure fresh display */
        ratingsList.clear();
        /* This iterates over each review in the rating to populate the list */
        for (Review review : rating.getReviewList()) {
            Map<String, String> ratingMap = new HashMap<>();
            ratingMap.put("description", review.getDescription());
            ratingMap.put("ratingStars", String.format("%d Star", review.getRatingStars()));
            ratingsList.add(ratingMap);
        }

        if(rating.getNumReview()==0){
            TextView emptyTextView = getView().findViewById(R.id.emptyListTextView);
            emptyTextView.setVisibility(View.VISIBLE);
        }
        else{
            TextView emptyTextView = getView().findViewById(R.id.emptyListTextView);
            emptyTextView.setVisibility(View.INVISIBLE);
        }

        /* Displaying the average rating in a TextView */
        TextView averageRatingTextView = getView().findViewById(R.id.averageRatingTextView);
        String averageRatingText = String.format("Average Rating: %.2f", rating.getAverageStarRating());
        averageRatingTextView.setText(averageRatingText);


        /* Displaying the total number of reviews in a TextView */
        TextView totalNumberOfReviews = getView().findViewById(R.id.totalReviewNum);
        totalNumberOfReviews.setText("Total Reviews: "+rating.getNumReview());

        /* Setting up the adapter to display the ratings and descriptions in the ListView */
        ratingsAdapter = new SimpleAdapter(getContext(), ratingsList, R.layout.rating_item,
                new String[]{"description", "ratingStars"}, new int[]{R.id.ratingDescription, R.id.ratingStars});
        ratingsListView.setAdapter(ratingsAdapter);
    }

    /* Loads the ratings from the database and populates the ListView */
    private void loadRatings() {
        /* Retrieve the logged-in user's email from shared preferences */
        SharedPreferences sp = getActivity().getSharedPreferences("session_login", Context.MODE_PRIVATE);
        String loggedInUserEmail = sp.getString("email", "");
        /* Accessing the Firebase database reference for ratings */
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Ratings");
        dbRef.child(loggedInUserEmail.replace(".", ",")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /* Converting the dataSnapshot into a Rating object */
                Rating rating = dataSnapshot.getValue(Rating.class);
                /* Displaying the retrieved ratings on the UI if not null */
                if (rating != null) {
                    displayRatings(rating);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                /*
                * We are swallowing this error.
                 */
            }
        });
    }
}
