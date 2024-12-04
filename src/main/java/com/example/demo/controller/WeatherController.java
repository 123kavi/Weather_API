package com.example.demo.controller;

import com.example.demo.model.WeatherSummary;
import com.example.demo.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public CompletableFuture<WeatherSummary> getWeatherSummary(@RequestParam String city) {
        return weatherService.getWeatherSummary(city);
    }
}
