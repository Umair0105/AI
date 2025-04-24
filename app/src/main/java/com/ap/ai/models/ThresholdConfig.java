package com.ap.ai.models;

public class ThresholdConfig {
    private int heartRateMin;
    private int heartRateMax;
    private int systolicMin;
    private int systolicMax;
    private int diastolicMin;
    private int diastolicMax;
    private float temperatureMin;
    private float temperatureMax;
    private int oxygenLevelMin;
    private int oxygenLevelMax;

    public ThresholdConfig() {
        // Default values
        this.heartRateMin = 60;
        this.heartRateMax = 100;
        this.systolicMin = 90;
        this.systolicMax = 140;
        this.diastolicMin = 60;
        this.diastolicMax = 90;
        this.temperatureMin = 36.1f;
        this.temperatureMax = 37.2f;
        this.oxygenLevelMin = 95;
        this.oxygenLevelMax = 100;
    }

    // Getters and Setters
    public int getHeartRateMin() { return heartRateMin; }
    public void setHeartRateMin(int heartRateMin) { this.heartRateMin = heartRateMin; }

    public int getHeartRateMax() { return heartRateMax; }
    public void setHeartRateMax(int heartRateMax) { this.heartRateMax = heartRateMax; }

    public int getSystolicMin() { return systolicMin; }
    public void setSystolicMin(int systolicMin) { this.systolicMin = systolicMin; }

    public int getSystolicMax() { return systolicMax; }
    public void setSystolicMax(int systolicMax) { this.systolicMax = systolicMax; }

    public int getDiastolicMin() { return diastolicMin; }
    public void setDiastolicMin(int diastolicMin) { this.diastolicMin = diastolicMin; }

    public int getDiastolicMax() { return diastolicMax; }
    public void setDiastolicMax(int diastolicMax) { this.diastolicMax = diastolicMax; }

    public float getTemperatureMin() { return temperatureMin; }
    public void setTemperatureMin(float temperatureMin) { this.temperatureMin = temperatureMin; }

    public float getTemperatureMax() { return temperatureMax; }
    public void setTemperatureMax(float temperatureMax) { this.temperatureMax = temperatureMax; }

    public int getOxygenLevelMin() { return oxygenLevelMin; }
    public void setOxygenLevelMin(int oxygenLevelMin) { this.oxygenLevelMin = oxygenLevelMin; }

    public int getOxygenLevelMax() { return oxygenLevelMax; }
    public void setOxygenLevelMax(int oxygenLevelMax) { this.oxygenLevelMax = oxygenLevelMax; }
} 