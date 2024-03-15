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
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.quickcash.Objects.Job;
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

        SharedPreferences sharedPref = requireActivity().getSharedPreferences(
                getString(R.string.sessionData_spID), Context.MODE_PRIVATE);
        String userRole = sharedPref.getString("role", "none");
        /*
        Displays or hides the preferences button based on the user's role
        User has to be an employee in order to access preferences
         */
        if ("Employee".equals(userRole)) {
            binding.preferencesButton.setVisibility(View.VISIBLE);
            binding.preferencesButton.setOnClickListener(view -> {
                NavController navController = NavHostFragment.findNavController(this);
                navController.navigate(R.id.employeePreferredJobs);
            });
        } else {
            binding.preferencesButton.setVisibility(View.GONE);
        }

        binding.settingsLogoutButton.setOnClickListener(view -> {
            Snackbar.make(view, "Logging out!", Snackbar.LENGTH_SHORT).show();
            handleLogout();
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
}