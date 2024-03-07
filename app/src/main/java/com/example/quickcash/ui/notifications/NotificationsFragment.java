package com.example.quickcash.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quickcash.FirebaseStuff.JobDBHelper;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.JobTypes;
import com.example.quickcash.R;
import com.example.quickcash.databinding.FragmentNotificationsBinding;
import com.google.android.material.snackbar.Snackbar;
import com.example.quickcash.Activities.MainActivity;

import java.time.Duration;
import java.util.Random;

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
                Random random = new Random();
                JobTypes jobTypes = JobTypes.values()[random.nextInt(11)];
                Duration duration = Duration.ZERO.plusMillis(random.nextInt(999999999));
                Job job = new Job(getRandomGibberish(random), getRandomGibberish(random), jobTypes, random.nextDouble(), duration.toString(), "Loki360@gmail.com");
                JobDBHelper jobDBHelper = new JobDBHelper(job);
                jobDBHelper.pushJobToDB();
                Snackbar.make(binding.pushJobToDbButon, "Pushed job to database!", Snackbar.LENGTH_SHORT).show();
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