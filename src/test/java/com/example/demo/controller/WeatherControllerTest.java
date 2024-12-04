package com.example.demo.controller;

import com.example.demo.model.WeatherSummary;
import com.example.demo.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class WeatherControllerTest {

    @InjectMocks
    private WeatherController weatherController;

    @Mock
    private WeatherService weatherService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();
    }

    @Test
    void testGetWeatherSummary() throws Exception {
        String city = "London";
        WeatherSummary mockSummary = new WeatherSummary(city, 20.5, null, null);

        // Mock the service response
        when(weatherService.getWeatherSummary(city)).thenReturn(CompletableFuture.completedFuture(mockSummary));

        // Perform the GET request
        mockMvc.perform(get("/weather").param("city", city))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value(city))
                .andExpect(jsonPath("$.averageTemperature").value(20.5));

        // Verify that the service method was called once
        verify(weatherService, times(1)).getWeatherSummary(city);
    }

    @Test
    void testGetWeatherSummaryWithInvalidCity() throws Exception {
        // Test when the city parameter is missing or invalid
        mockMvc.perform(get("/weather").param("city", ""))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalArgumentException));
    }

    @Test
    void testGetWeatherSummaryServiceException() throws Exception {
        String city = "InvalidCity";

        // Mock the service to throw an exception
        when(weatherService.getWeatherSummary(city)).thenThrow(new RuntimeException("Service error"));

        // Perform the GET request
        mockMvc.perform(get("/weather").param("city", city))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof RuntimeException));
    }
}
