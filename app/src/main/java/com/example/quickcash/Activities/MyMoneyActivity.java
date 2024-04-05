package com.example.quickcash.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.test.espresso.remote.EspressoRemoteMessage;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quickcash.FirebaseStuff.PaymentsDBHelper;
import com.example.quickcash.Objects.PaymentInfo;
import com.example.quickcash.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseError;

import java.text.ParseException;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MyMoneyActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    private LineChart chart;
    private SeekBar seekBarX;
    private TextView tvX;

    private List<PaymentInfo> paymentList;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_money);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.our_toolbar);

        SharedPreferences sharedPreferences = this.getSharedPreferences(getResources().getString(R.string.sessionData_spID), Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "Error - cannot get email");
        userName = sharedPreferences.getString("username", "Error - can't fetch your names yet...");

        StringBuilder titleBuilder = new StringBuilder();
        titleBuilder.append(userName);
        titleBuilder.append("'s income chart");
        myToolbar.setTitle(titleBuilder.toString());

        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        PaymentsDBHelper paymentsDBHelper = new PaymentsDBHelper();
        paymentsDBHelper.getAllPayments(email, new PaymentsDBHelper.PaymentObjectCallback() {
            @Override
            public void onPaymentsReceived(List<PaymentInfo> paymentInfos) {
                paymentList = paymentInfos;

                updateChart();
            }

            @Override
            public void onError(DatabaseError error) {
                Toast.makeText(getApplicationContext(), "A Database Error has occurred: "+error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        tvX = findViewById(R.id.tvXMax);

        seekBarX = findViewById(R.id.seekBar1);
        seekBarX.setOnSeekBarChangeListener(this);
        seekBarX.setMax(91);

        // // Chart Style // //
        chart = findViewById(R.id.chart1);

        // background color
        chart.setBackgroundColor(Color.WHITE);

        // disable description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // set listeners
        chart.setOnChartValueSelectedListener(this);
        chart.setDrawGridBackground(false);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // force pinch zoom along both axis
        chart.setPinchZoom(true);

        XAxis xAxis;
        // // X-Axis Style // //
        xAxis = chart.getXAxis();

        // vertical grid lines
        xAxis.enableGridDashedLine(10f, 10f, 0f);

        YAxis yAxis;
        // // Y-Axis Style // //
        yAxis = chart.getAxisLeft();

        // disable dual axis (only use LEFT axis)
        chart.getAxisRight().setEnabled(false);

        // horizontal grid lines
        yAxis.enableGridDashedLine(10f, 10f, 0f);

        // axis range
        yAxis.setAxisMinimum(0f);

        // add data
        seekBarX.setProgress(8);

        // draw points over time
        chart.animateX(1500);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // draw legend entries as lines
        l.setForm(Legend.LegendForm.LINE);
    }

    private void updateChart() {
        List<Entry> values;
        int daysToLook = seekBarX.getProgress();
        if (daysToLook == seekBarX.getMax()) {
            values = getEntriesForEternity();
        } else {
            values = getEntriesForPastNumberOfDay(daysToLook);
        }

        for (PaymentInfo info : paymentList) {
            Log.i("My purse", "Received info - " + info.getDate() + " - " + info.getDoubleAmount());
        }

        StringBuilder labelBuilder = new StringBuilder();
        labelBuilder.append(userName);
        labelBuilder.append("'s income against time");

        LineDataSet set;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set.setValues(values);
            set.notifyDataSetChanged();
            set.setLabel(labelBuilder.toString());
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set = new LineDataSet(values, labelBuilder.toString());

            set.setDrawIcons(false);

            // draw dashed line
            set.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set.setColor(Color.BLACK);
            set.setCircleColor(Color.BLACK);

            // line thickness and point size
            set.setLineWidth(1f);
            set.setCircleRadius(3f);

            // draw points as solid circles
            set.setDrawCircleHole(false);

            // customize legend entry
            set.setFormLineWidth(1f);
            set.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set.setFormSize(15.f);

            // text size of values
            set.setValueTextSize(9f);

            // draw selection line as dashed
            set.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            set.setDrawFilled(true);
            set.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.shadow);
                set.setFillDrawable(drawable);
            } else {
                set.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            chart.setData(data);
        }
    }

    private List<Entry> getEntriesForPastNumberOfDay(int numDays) {
        Date today = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(today);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - numDays + 1);
        List<Entry> values = new ArrayList<>();
        for (int i= 0; i < numDays; i++) {
            Date date = calendar.getTime();
            float value = 0;
            for (PaymentInfo info : paymentList) {
                try {
                    Date paymentDate = info.getDateObject();
                    if (date.getDate() == paymentDate.getDate() && date.getMonth() == paymentDate.getMonth() && date.getYear() == paymentDate.getYear()) {
                        value += (float) info.getDoubleAmount();
                    }
                } catch (ParseException exception) {
                    Log.e("My Purse", "Cannot parse " + info.getDate());
                    Log.e("My Purse", exception.getMessage() + "\n" + exception.toString());
                }
            }
            values.add(new Entry(i, value, getResources().getDrawable(R.drawable.icon_menhera_view)));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        return values;
    }

    private List<Entry> getEntriesForEternity() {
        Date today = new Date();
        Date date = new Date();
        for (PaymentInfo info : paymentList) {
            try {
                if (info.getDateObject().before(date)) {
                    date = info.getDateObject();
                }
            } catch (ParseException exception) {
                Log.e("My Purse (Forever)", "Cannot parse " + info.getDate());
                Log.e("My Purse (Forever)", exception.getMessage() + "\n" + exception.toString());
            }
        }

        Duration duration = Duration.between(date.toInstant(), today.toInstant());
        return getEntriesForPastNumberOfDay((int) duration.toDays());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (seekBarX.getProgress() == seekBarX.getMax()) {
            tvX.setText("Since time immemorial");
        } else {
            StringBuilder tvXBuilder = new StringBuilder();
            tvXBuilder.append("Last ");
            tvXBuilder.append(seekBarX.getProgress()-1);
            tvXBuilder.append(" days");
            tvX.setText(tvXBuilder.toString());
        }

        if (paymentList != null) {
            updateChart();

            // redraw
            chart.invalidate();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOW HIGH", "low: " + chart.getLowestVisibleX() + ", high: " + chart.getHighestVisibleX());
        Log.i("MIN MAX", "xMin: " + chart.getXChartMin() + ", xMax: " + chart.getXChartMax() + ", yMin: " + chart.getYChartMin() + ", yMax: " + chart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }
}