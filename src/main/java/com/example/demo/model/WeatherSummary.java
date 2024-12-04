package com.example.demo.model;

import java.time.LocalDate;

public class WeatherSummary {
    private String city;
    private double averageTemperature;
    private LocalDate hottestDay;
    private LocalDate coldestDay;

    // Constructors, Getters, and Setters
    public WeatherSummary(String city, double averageTemperature, LocalDate hottestDay, LocalDate coldestDay) {
        this.city = city;
        this.averageTemperature = averageTemperature;
        this.hottestDay = hottestDay;
        this.coldestDay = coldestDay;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getAverageTemperature() {
        return averageTemperature;
    }

    public void setAverageTemperature(double averageTemperature) {
        this.averageTemperature = averageTemperature;
    }

    public LocalDate getHottestDay() {
        return hottestDay;
    }

    public void setHottestDay(LocalDate hottestDay) {
        this.hottestDay = hottestDay;
    }

    public LocalDate getColdestDay() {
        return coldestDay;
    }

    public void setColdestDay(LocalDate coldestDay) {
        this.coldestDay = coldestDay;
    }

    @Override
    public String toString() {
        return "WeatherSummary{" +
                "city='" + city + '\'' +
                ", averageTemperature=" + averageTemperature +
                ", hottestDay=" + hottestDay +
                ", coldestDay=" + coldestDay +
                '}';
    }
}
