package com.ap.ai;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.ap.ai.models.DashboardSettings;
import com.ap.ai.models.HealthReading;
import com.ap.ai.models.MetricType;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {

    private DashboardViewModel viewModel;
    private LineChart chart;
    private TextView currentValueText;
    private TextView lastUpdateText;
    private RadioGroup metricRadioGroup;
    private Switch pauseSwitch;
    private SimpleDateFormat dateFormat;
    private MetricType currentMetricType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize views
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        chart = findViewById(R.id.chart);
        currentValueText = findViewById(R.id.currentValueText);
        lastUpdateText = findViewById(R.id.lastUpdateText);
        metricRadioGroup = findViewById(R.id.metricRadioGroup);
        pauseSwitch = findViewById(R.id.pauseSwitch);
        FloatingActionButton settingsFab = findViewById(R.id.settingsFab);

        // Initialize variables
        dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        currentMetricType = MetricType.HEART_RATE;

        // Setup ViewModel
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        setupChart();
        setupControls();
        observeData();

//        settingsFab.setOnClickListener(v ->
//            startActivity(new Intent(this, SettingsActivity.class)));
    }

    private void setupChart() {
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return dateFormat.format(new Date((long) value));
            }
        });
    }

    private void setupControls() {
        metricRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.heartRateRadio) {
                currentMetricType = MetricType.HEART_RATE;
            } else if (checkedId == R.id.bloodPressureRadio) {
                currentMetricType = MetricType.BLOOD_PRESSURE;
            }
            viewModel.setMetricType(currentMetricType);
        });

        pauseSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> 
            viewModel.setPaused(isChecked));
    }

    private void observeData() {
        viewModel.getReadings().observe(this, this::updateChart);
        
        viewModel.getAlerts().observe(this, alert -> {
            Snackbar.make(findViewById(android.R.id.content), 
                         alert.getMessage(), 
                         Snackbar.LENGTH_LONG)
                    .setAction("View", v -> 
                        startActivity(new Intent(this, AlertsActivity.class)))
                    .show();
        });
    }

    private void updateChart(List<HealthReading> readings) {
        if (readings.isEmpty()) return;

        List<Entry> entries = new ArrayList<>();
        for (HealthReading reading : readings) {
            entries.add(new Entry(reading.getTimestamp().getTime(), reading.getValue()));
        }

        LineDataSet dataSet = new LineDataSet(entries, currentMetricType.toString());
        dataSet.setColor(getResources().getColor(R.color.purple_500));
        dataSet.setCircleColor(getResources().getColor(R.color.purple_500));
        dataSet.setDrawValues(false);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();

        // Update current value
        HealthReading latestReading = readings.get(readings.size() - 1);
        updateCurrentValue(latestReading);
    }

    private void updateCurrentValue(HealthReading reading) {
        String value;
        switch (reading.getType()) {
            case HEART_RATE:
                value = (int) reading.getValue() + " BPM";
                break;
            case BLOOD_PRESSURE:
                value = reading.getSystolic() + "/" + reading.getDiastolic() + " mmHg";
                break;
            case TEMPERATURE:
                if (viewModel.getUnitSystem() == DashboardSettings.UnitSystem.METRIC) {
                    value = String.format(Locale.getDefault(), "%.1f°C", reading.getValue());
                } else {
                    value = String.format(Locale.getDefault(), "%.1f°F", 
                                        reading.getValue() * 9/5 + 32);
                }
                break;
            case OXYGEN_LEVEL:
                value = (int) reading.getValue() + "%";
                break;
            default:
                value = String.valueOf(reading.getValue());
        }

        currentValueText.setText(value);
        lastUpdateText.setText("Last updated: " + 
                             dateFormat.format(reading.getTimestamp()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_alerts) {
            startActivity(new Intent(this, AlertsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
} 