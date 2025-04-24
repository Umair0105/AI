package com.ap.ai.models;

public class DashboardSettings {
    private boolean darkMode;
    private UnitSystem unitSystem;
    private boolean simulationMode;
    private ThresholdConfig thresholds;
    private String chartType;

    public DashboardSettings() {
        this.darkMode = false;
        this.unitSystem = UnitSystem.METRIC;
        this.simulationMode = false;
        this.thresholds = new ThresholdConfig();
        this.chartType = "LINE";
    }

    // Getters and Setters
    public boolean isDarkMode() { return darkMode; }
    public void setDarkMode(boolean darkMode) { this.darkMode = darkMode; }

    public UnitSystem getUnitSystem() { return unitSystem; }
    public void setUnitSystem(UnitSystem unitSystem) { this.unitSystem = unitSystem; }

    public boolean isSimulationMode() { return simulationMode; }
    public void setSimulationMode(boolean simulationMode) { this.simulationMode = simulationMode; }

    public ThresholdConfig getThresholds() { return thresholds; }
    public void setThresholds(ThresholdConfig thresholds) { this.thresholds = thresholds; }

    public String getChartType() { return chartType; }
    public void setChartType(String chartType) { this.chartType = chartType; }

    public enum UnitSystem {
        METRIC,
        IMPERIAL
    }
} 