package com.ap.ai.models;

import java.util.Date;

public class Alert {
    private String id;
    private HealthReading reading;
    private String message;
    private Date timestamp;

    public Alert(String id, HealthReading reading, String message, Date timestamp) {
        this.id = id;
        this.reading = reading;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public HealthReading getReading() { return reading; }
    public String getMessage() { return message; }
    public Date getTimestamp() { return timestamp; }
} 