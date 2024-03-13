package com.example.quickcash.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.quickcash.R;

import java.util.Calendar;
import java.util.Locale;

public class FilterActivity extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private Spinner jobTypeFilter;
    private EditText salaryFilter;
    private EditText durationFilter;
    private Button dateFilter;
    private SeekBar distanceFilter;
    private TextView distanceValue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view= inflater.inflate(R.layout.fragment_filter, container, false);

        jobTypeFilter = view.findViewById(R.id.jobTypeFilter);
        salaryFilter = view.findViewById(R.id.salaryFilter);
        durationFilter = view.findViewById(R.id.durationFilter);
        dateFilter = view.findViewById(R.id.dateFilter);
        distanceFilter = view.findViewById(R.id.distanceFilter);
        distanceValue = view.findViewById(R.id.distanceValue);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.job_types_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobTypeFilter.setAdapter(adapter);

        distanceValue.setText(String.valueOf(distanceFilter.getProgress()));
        distanceFilter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distanceValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        /*
        The implementation of datepicker was aided by
        https://developer.android.com/develop/ui/views/components/pickers
        https://chat.openai.com/share/f6171ce7-a2c9-4a8d-9e65-ee7f26ff475c
        */
        dateFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        return view;
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and show it
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireActivity(), this, year, month, day);
        datePickerDialog.show();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
        dateFilter.setText(selectedDate);
    }
}
