package com.example.quickcash.ui.employeePreferredJobs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.quickcash.FirebaseStuff.FirebasePreferredJobsHelper;
import com.example.quickcash.Objects.JobTypes;
import com.example.quickcash.Objects.PreferredJobs;
import com.example.quickcash.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployeePreferredJobsFragment extends Fragment {
    private FirebasePreferredJobsHelper firebaseHelper;
    private String userEmail;
    private EmployeePreferredAdapter adapter;
    private List<Boolean> checkedStates;
    private static final String CHECKED_STATE_KEY = "CHECKED_STATE";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_employeepreferredlist, container, false);
        /*
        Sets up the UI components and initializes the united state of the fragment
         */
        initializeViews(root);
        initializeUserData();
        /*
         retrieves and dispatches the saved preferences on the UI
         */
        loadSavedPreferences();
        /*
        sets up listeners for buttons and their corresponding actions.
         */
        setupListeners(root);
        return root;
    }

    /*
    initializes the ListView and adapter for job types and unchecked states
     */
    private void initializeViews(View root) {
        ListView listView = root.findViewById(R.id.preferredJobTypeList);
        List<JobTypes> jobTypesList = new ArrayList<>(Arrays.asList(JobTypes.values()));
        jobTypesList.remove(JobTypes.UNDEFINED);

        /*
        initialize all job types as unchecked (which is essentially false for checked states)
         */
        checkedStates = new ArrayList<>(jobTypesList.size());
        for (int i = 0; i < jobTypesList.size(); i++) {
            checkedStates.add(false);
        }

        adapter = new EmployeePreferredAdapter(getActivity(), jobTypesList, checkedStates);
        listView.setAdapter(adapter);

    }
    /*
    if the user email (employee email in this case) is not set,
    this method grabs it from shared preferences
     */
    private void initializeUserData() {
        if (userEmail == null || userEmail.isEmpty()) {
            SharedPreferences sharedPref = requireActivity().getSharedPreferences(getString(R.string.sessionData_spID), Context.MODE_PRIVATE);
            userEmail = sharedPref.getString("email", "");
        }
        /*
        initializes firebaseHelper if its not set
         */
        if (firebaseHelper == null) {
            firebaseHelper = new FirebasePreferredJobsHelper();
        }
    }

    /*
    for testing purposes
     */
    public void setTestEmail(String testEmail) {
        this.userEmail = testEmail;
    }

    /*
    loads up saved preferences from that specific firebase user branch
     */
    private void loadSavedPreferences() {
        if (!userEmail.isEmpty()) {
            firebaseHelper.retrievePreferredJobs(userEmail, preferredJobs -> {
                for (int i = 0; i < adapter.getCount(); i++) {
                    JobTypes jobType = adapter.getItem(i);
                    if (preferredJobs.contains(jobType.name())) {
                        checkedStates.set(i, true);
                    }
                }
                adapter.notifyDataSetChanged();
            });
        }
    }

    /*
    method for listeners (back button and save preferences button)
     */
    private void setupListeners(View root) {
        Button saveButton = root.findViewById(R.id.savePreferencesButton);
        Button backButton = root.findViewById(R.id.backButtonFromPreferences);
        backButton.setOnClickListener(view -> getParentFragmentManager().popBackStack());
        saveButton.setOnClickListener(view -> savePreferences());
    }

    /*
    method for saving the current preferences to Firebase
     */
    private void savePreferences() {
        /*
        Checks if a user email is set before saving preferences
         */
        if (!userEmail.isEmpty()) {
            PreferredJobs preferredJobs = new PreferredJobs(userEmail);
            List<Boolean> checkedStates = adapter.getCheckedStates();
            /*
             iterates through the checked states
             and add (checks/ticks) the checked job types to preferredJobs
             */
            for (int i = 0; i < adapter.getCount(); i++) {
                if (checkedStates.get(i)) {
                    preferredJobs.checkJob(adapter.getItem(i));
                }
            }

            /*
             saves the preferred jobs using the Firebase helper
             */
            firebaseHelper.savePreferredJobs(preferredJobs);
            Toast.makeText(getContext(), "Preferences saved!", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    This method-
    saves the states of checkboxes (checked or unchecked) into SharedPreferences.
    Each checkbox state is saved with a unique key constructed by concatenating
    "CHECKED_STATE" with the checkbox's position in the list.
    This process allows-
    each checkbox's state to be individually saved and retrieved later.
    */
    private void saveCheckedStates() {
        SharedPreferences preferences = requireActivity().getSharedPreferences(
                getString(R.string.sessionData_spID), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        List<Boolean> checkedStates = adapter.getCheckedStates();
        for (int i = 0; i < checkedStates.size(); i++) {
            editor.putBoolean(CHECKED_STATE_KEY  + i, checkedStates.get(i));
        }
        editor.apply();
    }

    /*
    This method-
    retrieves the saved states of checkboxes from SharedPreferences using their unique keys.
    The unique key for each checkbox is constructed the same way as when saving -
    "CHECKED_STATE" concatenated with the checkbox's position.
    This process ensures-
    that the correct state is retrieved for each checkbox based on its position in the list.
    */
    private void restoreCheckedStates() {
        SharedPreferences preferences = requireActivity().getSharedPreferences(
                getString(R.string.sessionData_spID), Context.MODE_PRIVATE);
        checkedStates = new ArrayList<>(adapter.getCount());

        for (int i = 0; i < adapter.getCount(); i++) {
            checkedStates.add(preferences.getBoolean(CHECKED_STATE_KEY + i, false));
        }
        adapter.setCheckedStates(checkedStates);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        /*
         saves the checked states when the fragment is paused
         */
        saveCheckedStates();
    }

    @Override
    public void onResume() {
        super.onResume();
        /*
         restores the checked states when the fragment is resumed
         */
        restoreCheckedStates();
    }
}



