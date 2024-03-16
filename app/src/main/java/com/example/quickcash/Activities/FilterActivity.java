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
import androidx.lifecycle.ViewModelProvider;

import com.example.quickcash.Objects.Filters.DateFilter;
import com.example.quickcash.Objects.Filters.DistanceFilter;
import com.example.quickcash.Objects.Filters.DurationFilter;
import com.example.quickcash.Objects.Filters.IFilter;
import com.example.quickcash.Objects.Filters.JobTypeFilter;
import com.example.quickcash.Objects.Filters.SalaryFilter;
import com.example.quickcash.Objects.JobTypes;
import com.example.quickcash.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FilterActivity extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    public interface OnDistanceSelectedListener {
        void onDistanceSelected(int distance);
    }


    private Spinner jobTypeFilter;
    private EditText salaryFilter;
    private EditText durationFilter;
    private Button dateFilter;
    private SeekBar distanceFilter;
    private TextView distanceValue;
    private Date selectedDate;
    private List<IFilter> filterList;
    private FilterCompleteCallback callback;

    public static class FilterCompleteCallback {
        public void onResult(List<IFilter> filters) {}
    }

    public void setCallback(FilterCompleteCallback callback) {
        this.callback = callback;
    }
    public FilterCompleteCallback getCallback() {
        return callback;
    }

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

        // Set job type
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.job_types_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobTypeFilter.setAdapter(adapter);

        // Set distance
        distanceValue.setText(String.valueOf(distanceFilter.getProgress()));
        /*
        james look through this, this is how i try to use the seekbar press of the map button to get filtered distance
         */
        distanceFilter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distanceValue.setText(String.valueOf(progress));
//                MapViewModel mapViewModel = new ViewModelProvider(getActivity()).get(MapViewModel.class);
//                mapViewModel.setSelectedDistance(progress);
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

        Button applyButton = (Button) view.findViewById(R.id.applyFilterButton);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IFilter filterJobType = null;
                if (jobTypeFilter.getSelectedItemPosition() != 0) {
                    // Create a filter for that job
                    String jobTypeString = jobTypeFilter.getSelectedItem().toString();
                    JobTypes jobType = JobTypes.valueOf(jobTypeString);
                    filterJobType = new JobTypeFilter();
                    filterJobType.setValue(jobType);
                }
                IFilter filterSalary = null;
                if (salaryFilter.getText().length()!=0){
                    Double salaryDouble = Double.valueOf(salaryFilter.getText().toString());
                    filterSalary = new SalaryFilter();
                    filterSalary.setValue(salaryDouble);
                }
                IFilter filterDuration = null;
                if (durationFilter.getText().length()!=0){
                    Integer durationInt = Integer.valueOf(durationFilter.getText().toString());
                    filterDuration = new DurationFilter();
                    filterDuration.setValue(durationInt);
                }
                IFilter filterDate = null;
                if (dateFilter!=null){
                    Date dateDate = selectedDate;
                    filterDate = new DateFilter();
                    filterDate.setValue(dateDate);
                }
                IFilter filterDistance = new DistanceFilter();
                filterDistance.setValue(filterDistance.getValue());

                if (filterList == null) {
                    filterList = new ArrayList<>();
                }

                if (filterJobType != null) {
                    filterList.add(filterJobType);
                };
                if (filterSalary != null){
                    filterList.add(filterSalary);
                }
                if (filterDuration != null) {
                    filterList.add(filterDuration);
                }
                if (filterDate!=null){
                    filterList.add(filterDate);
                }
                filterList.add(filterDistance);

                if (callback != null) {
                    callback.onResult(filterList);
                }
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
        this.selectedDate = new Date(year, month, dayOfMonth);
    }
}
