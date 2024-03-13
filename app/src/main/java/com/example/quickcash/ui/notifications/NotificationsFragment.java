package com.example.quickcash.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quickcash.FirebaseStuff.JobDBHelper;
import com.example.quickcash.Objects.Filters.FilterHelper;
import com.example.quickcash.Objects.Filters.IFilter;
import com.example.quickcash.Objects.Filters.TitleFilter;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.JobTypes;
import com.example.quickcash.R;
import com.example.quickcash.databinding.FragmentNotificationsBinding;
import com.google.android.material.snackbar.Snackbar;
import com.example.quickcash.Activities.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        textView.setText("Settings fragment");
        final Button logOutButton = binding.settingsLogoutButton;
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Logging out!", Snackbar.LENGTH_SHORT).show();
                handleLogout();
            }
        });
        binding.pushJobToDbButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Random random = new Random();
//                JobTypes jobTypes = JobTypes.values()[random.nextInt(11)];
//                Duration duration = Duration.ZERO.plusMillis(random.nextInt(999999999));
//                Job job = new Job(getRandomGibberish(random), getRandomGibberish(random), jobTypes, random.nextDouble(), duration.toString(), "Loki360@gmail.com");
//                JobDBHelper jobDBHelper = new JobDBHelper(job);
//                jobDBHelper.pushJobToDB();
//                Snackbar.make(binding.pushJobToDbButon, "Pushed job to database!", Snackbar.LENGTH_SHORT).show();
                List<IFilter> filters = new ArrayList<>();
                TitleFilter titleFilter = new TitleFilter();
                titleFilter.setValue("oui");
                filters.add(titleFilter);

                FilterHelper helper = new FilterHelper();
                FilterHelper.FilterHelperCallback callback = new FilterHelper.FilterHelperCallback() {
                    @Override
                    public void onResult(Set<Job> searchResult) {
                        boolean foundOuiJob = false;
                        for (Job job : searchResult) {
                            if (job.getTitle().equals("I want to see a magic show!")) {
                                foundOuiJob = true;
                                break;
                            }
                        }
                        Log.d("App", "foundOuiJob is " + String.valueOf(foundOuiJob));
                    }
                };
                helper.setCallback(callback);
                helper.setFilters(filters);
                helper.run();
            }
        });
        binding.testSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fireBaseURL = "https://quickcash-6941c-default-rtdb.firebaseio.com/"; //For some reason I was getting null pointer exceptions when I tried to reference strings.xml so I manually added the link here.
                FirebaseDatabase database = FirebaseDatabase.getInstance(fireBaseURL);
                DatabaseReference reference = database.getReference("Posted Jobs");

                Query filteredRef = reference.orderByChild("title").equalTo("oui");
                filteredRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            Job obj = snapshot1.getValue(Job.class); // This might need adjustment
                            if (obj != null ) {
                                binding.textNotifications.setText(binding.textNotifications.getText() + "\n" + obj.getTitle());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void handleLogout() {
        Context context = requireContext().getApplicationContext();
        SharedPreferences sp = context.getSharedPreferences(getResources().getString(R.string.sessionData_spID), Context.MODE_PRIVATE);
        sp.edit().clear().apply();

        Intent intent = new Intent(requireContext().getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private String getRandomGibberish(Random random) {
        StringBuilder builder = new StringBuilder();
        try {
            builder.append(Character.getName(random.nextInt(Character.FINAL_QUOTE_PUNCTUATION)));
        }  catch (Exception e) {
            for (int i = 0; i < 16; i++) {
                String s = e.toString();
                builder.append(s.charAt(random.nextInt(s.length())));
            }
        }
        builder.append(random.nextInt(999999));
        return builder.toString();
    }
}