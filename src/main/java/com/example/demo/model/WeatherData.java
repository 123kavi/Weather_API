package com.example.demo.model;

import java.time.LocalDate;

public class WeatherData {
    private LocalDate date;
    private double temperature;

    // Default Constructor
    public WeatherData() {
    }

    // Parameterized Constructor
    public WeatherData(LocalDate date, double temperature) {
        this.date = date;
        this.temperature = temperature;
    }

    // Getter for Date
    public LocalDate getDate() {
        return date;
    }

    // Setter for Date
    public void setDate(LocalDate date) {
        this.date = date;
    }

    // Getter for Temperature
    public double getTemperature() {
        return temperature;
    }

    // Setter for Temperature
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    // toString Method for Debugging
    @Override
    public String toString() {
        return "WeatherData{" +
                "date=" + date +
                ", temperature=" + temperature +
                '}';
    }
}
