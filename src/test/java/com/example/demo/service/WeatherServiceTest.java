package com.example.demo.service;

import com.example.demo.client.ExternalWeatherApiClient;
import com.example.demo.model.WeatherSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class WeatherServiceTest {

    @InjectMocks
    private WeatherService weatherService;

    @Mock
    private ExternalWeatherApiClient weatherApiClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWeatherSummarySuccess() {
        String city = "London";
        WeatherSummary mockSummary = new WeatherSummary(city, 20.5, null, null);

        // Mock the external client response
        when(weatherApiClient.fetchWeatherSummary(city)).thenReturn(mockSummary);

        // Call the service method
        CompletableFuture<WeatherSummary> result = weatherService.getWeatherSummary(city);

        // Assert that the result matches the mock summary
        assertTrue(result.isDone());
        assertEquals(mockSummary, result.join());
    }

    @Test
    void testGetWeatherSummaryWithInvalidCity() {
        String city = "";

        // Call the service method
        try {
            weatherService.getWeatherSummary(city).join();
            fail("Expected exception due to invalid city");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Invalid city name"));
        }
    }

    @Test
    void testGetWeatherSummaryApiError() {
        String city = "InvalidCity";
        when(weatherApiClient.fetchWeatherSummary(city)).thenThrow(new RuntimeException("External API error"));

        try {
            weatherService.getWeatherSummary(city).join();
            fail("Expected exception due to API error");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("External API error"));
        }
    }
}
