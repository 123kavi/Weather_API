package com.example.demo.service;

import com.example.demo.client.ExternalWeatherApiClient;
import com.example.demo.model.WeatherSummary;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.concurrent.CompletableFuture;

@Service
public class WeatherService {

    private final ExternalWeatherApiClient weatherApiClient;

    public WeatherService(ExternalWeatherApiClient weatherApiClient) {
        this.weatherApiClient = weatherApiClient;
    }

    @Async
    @Cacheable(value = "weatherSummaryCache", key = "#city")
    public CompletableFuture<WeatherSummary> getWeatherSummary(String city) {
        try {
            // Validate city name (simple check)
            if (city == null || city.trim().isEmpty()) {
                throw new IllegalArgumentException("City name cannot be empty");
            }

            // Fetch weather summary from external API
            WeatherSummary summary = weatherApiClient.fetchWeatherSummary(city);
            return CompletableFuture.completedFuture(summary);

        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid city name: " + city, e);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Handle API errors (4xx or 5xx responses)
            throw new RuntimeException("External API error: " + e.getMessage(), e);
        } catch (Exception e) {
            // Handle other unexpected exceptions
            throw new RuntimeException("An error occurred while fetching weather data", e);
        }
    }
}
