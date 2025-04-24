package com.ap.ai;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.ap.ai.models.Alert;
import com.ap.ai.models.HealthReading;
import com.ap.ai.models.MetricType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AlertsViewModel extends ViewModel {
    private final MutableLiveData<List<Alert>> alerts;
    private final List<Alert> alertsList;

    public AlertsViewModel() {
        alertsList = new ArrayList<>();
        alerts = new MutableLiveData<>(new ArrayList<>());
        
        // For demo purposes, add some sample alerts
        if (alertsList.isEmpty()) {
            addSampleAlerts();
        }
    }

    public LiveData<List<Alert>> getAlerts() {
        return alerts;
    }

    public void addAlert(Alert alert) {
        alertsList.add(alert);
        alerts.setValue(new ArrayList<>(alertsList));
    }

    public void clearAlerts() {
        alertsList.clear();
        alerts.setValue(new ArrayList<>());
    }

    // This method is for demonstration purposes
    private void addSampleAlerts() {
        // Sample heart rate alert
        HealthReading heartReading = new HealthReading(
            UUID.randomUUID().toString(),
            MetricType.HEART_RATE,
            120f,
            null,
            null,
            new Date(System.currentTimeMillis() - 3600000), // 1 hour ago
            true
        );
        Alert heartAlert = new Alert(
            UUID.randomUUID().toString(),
            heartReading,
            "Abnormal heart rate: 120 BPM",
            heartReading.getTimestamp()
        );
        alertsList.add(heartAlert);

        // Sample blood pressure alert
        HealthReading bpReading = new HealthReading(
            UUID.randomUUID().toString(),
            MetricType.BLOOD_PRESSURE,
            150f,
            150,
            95,
            new Date(System.currentTimeMillis() - 7200000), // 2 hours ago
            true
        );
        Alert bpAlert = new Alert(
            UUID.randomUUID().toString(),
            bpReading,
            "Abnormal blood pressure: 150/95",
            bpReading.getTimestamp()
        );
        alertsList.add(bpAlert);

        // Sample temperature alert
        HealthReading tempReading = new HealthReading(
            UUID.randomUUID().toString(),
            MetricType.TEMPERATURE,
            38.5f,
            null,
            null,
            new Date(System.currentTimeMillis() - 10800000), // 3 hours ago
            true
        );
        Alert tempAlert = new Alert(
            UUID.randomUUID().toString(),
            tempReading,
            "Abnormal temperature: 38.5°C",
            tempReading.getTimestamp()
        );
        alertsList.add(tempAlert);

        alerts.setValue(new ArrayList<>(alertsList));
    }

    // Method to handle real backend data
    public void fetchAlertsFromBackend() {
        // TODO: Implement API call to fetch alerts from backend
        // For now, we're using sample data
    }

    // Method to export alerts
    public List<Alert> getAlertsForExport() {
        return new ArrayList<>(alertsList);
    }
} 