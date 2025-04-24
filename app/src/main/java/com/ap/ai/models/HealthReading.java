package com.ap.ai.models;

import java.util.Date;

public class HealthReading {
    private String id;
    private MetricType type;
    private float value;
    private Integer systolic;
    private Integer diastolic;
    private Date timestamp;
    private boolean isAbnormal;

    public HealthReading(String id, MetricType type, float value, Integer systolic, 
                        Integer diastolic, Date timestamp, boolean isAbnormal) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.timestamp = timestamp;
        this.isAbnormal = isAbnormal;
    }

    // Getters
    public String getId() { return id; }
    public MetricType getType() { return type; }
    public float getValue() { return value; }
    public Integer getSystolic() { return systolic; }
    public Integer getDiastolic() { return diastolic; }
    public Date getTimestamp() { return timestamp; }
    public boolean isAbnormal() { return isAbnormal; }
} 