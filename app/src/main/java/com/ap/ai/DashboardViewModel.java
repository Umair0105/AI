package com.ap.ai;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ap.ai.models.Alert;
import com.ap.ai.models.DashboardSettings;
import com.ap.ai.models.HealthReading;
import com.ap.ai.models.MetricType;
import com.ap.ai.models.ThresholdConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class DashboardViewModel extends ViewModel {
    private final MutableLiveData<List<HealthReading>> readings;
    private final MutableLiveData<Alert> alerts;
    private final List<HealthReading> storedReadings;
    private DashboardSettings settings;
    private MetricType currentMetricType;
    private boolean isPaused;
    private Timer updateTimer;

    public DashboardViewModel() {
        readings = new MutableLiveData<>(new ArrayList<>());
        alerts = new MutableLiveData<>();
        storedReadings = new ArrayList<>();
        settings = new DashboardSettings();
        currentMetricType = MetricType.HEART_RATE;
        isPaused = false;
        startUpdates();
    }

    public LiveData<List<HealthReading>> getReadings() {
        return readings;
    }

    public LiveData<Alert> getAlerts() {
        return alerts;
    }

    public DashboardSettings.UnitSystem getUnitSystem() {
        return settings.getUnitSystem();
    }

    public void setMetricType(MetricType type) {
        currentMetricType = type;
        updateReadings();
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
        if (!paused) {
            startUpdates();
        } else if (updateTimer != null) {
            updateTimer.cancel();
        }
    }

    private void startUpdates() {
        if (updateTimer != null) {
            updateTimer.cancel();
        }

        updateTimer = new Timer();
        updateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isPaused) {
                    if (settings.isSimulationMode()) {
                        addSimulatedReading();
                    } else {
                        // Here you would fetch real data from your backend
                        // fetchLatestReading();
                    }
                }
            }
        }, 0, 1000); // Update every second
    }

    private void addSimulatedReading() {
        HealthReading reading;
        switch (currentMetricType) {
            case HEART_RATE:
                reading = createSimulatedHeartRate();
                break;
            case BLOOD_PRESSURE:
                reading = createSimulatedBloodPressure();
                break;
            case TEMPERATURE:
                reading = createSimulatedTemperature();
                break;
            case OXYGEN_LEVEL:
                reading = createSimulatedOxygenLevel();
                break;
            default:
                return;
        }

        storedReadings.add(reading);

        // Keep only last 15 minutes of data
        long fifteenMinutesAgo = System.currentTimeMillis() - 15 * 60 * 1000;
        storedReadings.removeIf(r -> r.getTimestamp().getTime() < fifteenMinutesAgo);

        updateReadings();

        if (reading.isAbnormal()) {
            createAlert(reading);
        }
    }

    private void updateReadings() {
        List<HealthReading> filteredReadings = new ArrayList<>();
        for (HealthReading reading : storedReadings) {
            if (reading.getType() == currentMetricType) {
                filteredReadings.add(reading);
            }
        }
        readings.postValue(filteredReadings);
    }

    private void createAlert(HealthReading reading) {
        String message;
        switch (reading.getType()) {
            case HEART_RATE:
                message = "Abnormal heart rate: " + (int) reading.getValue() + " BPM";
                break;
            case BLOOD_PRESSURE:
                message = "Abnormal blood pressure: " + reading.getSystolic() + "/" + 
                         reading.getDiastolic();
                break;
            case TEMPERATURE:
                message = "Abnormal temperature: " + reading.getValue() + "°C";
                break;
            case OXYGEN_LEVEL:
                message = "Abnormal oxygen level: " + (int) reading.getValue() + "%";
                break;
            default:
                return;
        }

        Alert alert = new Alert(UUID.randomUUID().toString(), reading, message, new Date());
        alerts.postValue(alert);
    }

    private HealthReading createSimulatedHeartRate() {
        ThresholdConfig thresholds = settings.getThresholds();
        float value = (float) (Math.random() * (100 - 60) + 60);
        boolean isAbnormal = value < thresholds.getHeartRateMin() || 
                            value > thresholds.getHeartRateMax();

        return new HealthReading(
            UUID.randomUUID().toString(),
            MetricType.HEART_RATE,
            value,
            null,
            null,
            new Date(),
            isAbnormal
        );
    }

    private HealthReading createSimulatedBloodPressure() {
        ThresholdConfig thresholds = settings.getThresholds();
        int systolic = (int) (Math.random() * (140 - 90) + 90);
        int diastolic = (int) (Math.random() * (90 - 60) + 60);
        boolean isAbnormal = systolic < thresholds.getSystolicMin() || 
                            systolic > thresholds.getSystolicMax() ||
                            diastolic < thresholds.getDiastolicMin() || 
                            diastolic > thresholds.getDiastolicMax();

        return new HealthReading(
            UUID.randomUUID().toString(),
            MetricType.BLOOD_PRESSURE,
            systolic,
            systolic,
            diastolic,
            new Date(),
            isAbnormal
        );
    }

    private HealthReading createSimulatedTemperature() {
        ThresholdConfig thresholds = settings.getThresholds();
        float value = (float) (Math.random() * (37.2 - 36.1) + 36.1);
        boolean isAbnormal = value < thresholds.getTemperatureMin() || 
                            value > thresholds.getTemperatureMax();

        return new HealthReading(
            UUID.randomUUID().toString(),
            MetricType.TEMPERATURE,
            value,
            null,
            null,
            new Date(),
            isAbnormal
        );
    }

    private HealthReading createSimulatedOxygenLevel() {
        ThresholdConfig thresholds = settings.getThresholds();
        float value = (float) (Math.random() * (100 - 95) + 95);
        boolean isAbnormal = value < thresholds.getOxygenLevelMin() || 
                            value > thresholds.getOxygenLevelMax();

        return new HealthReading(
            UUID.randomUUID().toString(),
            MetricType.OXYGEN_LEVEL,
            value,
            null,
            null,
            new Date(),
            isAbnormal
        );
    }

    public void updateSettings(DashboardSettings newSettings) {
        settings = newSettings;
    }

    public void resetDashboard() {
        storedReadings.clear();
        updateReadings();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (updateTimer != null) {
            updateTimer.cancel();
        }
    }
} 