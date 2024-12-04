package com.example.demo.client;

import com.example.demo.model.OpenWeatherResponse;
import com.example.demo.model.WeatherData;
import com.example.demo.model.WeatherSummary;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExternalWeatherApiClient {

    private final WebClient webClient;

    public ExternalWeatherApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openweathermap.org/data/2.5").build();
    }

    public WeatherSummary fetchWeatherSummary(String city) {
        try {
            String apiKey = "1ba4931d3903c44cffa9c0e2d7d353bd"; // Replace with your OpenWeatherMap API key
            OpenWeatherResponse openWeatherResponse = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/forecast")
                            .queryParam("q", city)
                            .queryParam("appid", apiKey)
                            .build())
                    .retrieve()
                    .onStatus(httpStatus -> httpStatus.isError(),
                            clientResponse -> {
                                throw new RuntimeException("Error fetching data from OpenWeatherMap: " + clientResponse.statusCode());
                            })
                    .bodyToMono(OpenWeatherResponse.class)
                    .block();

            List<WeatherData> weatherDataList = openWeatherResponse.getList().stream()
                    .map(item -> new WeatherData(parseDate(item.getDtTxt()), item.getMain().getTemp()))
                    .collect(Collectors.toList());

            // Calculate the average temperature
            double averageTemperature = weatherDataList.stream()
                    .mapToDouble(WeatherData::getTemperature)
                    .average()
                    .orElse(0.0);

            // Find the hottest and coldest day
            WeatherData hottestDayData = weatherDataList.stream()
                    .max((wd1, wd2) -> Double.compare(wd1.getTemperature(), wd2.getTemperature()))
                    .orElseThrow(() -> new RuntimeException("Failed to find hottest day"));

            WeatherData coldestDayData = weatherDataList.stream()
                    .min((wd1, wd2) -> Double.compare(wd1.getTemperature(), wd2.getTemperature()))
                    .orElseThrow(() -> new RuntimeException("Failed to find coldest day"));

            // Create the WeatherSummary object
            WeatherSummary summary = new WeatherSummary(
                    city,
                    averageTemperature,
                    hottestDayData.getDate(),
                    coldestDayData.getDate()
            );

            return summary;

        } catch (WebClientResponseException e) {
            throw new RuntimeException("Failed to fetch weather data for city: " + city, e);
        }
    }

    // Helper method to parse the date string into LocalDate
    private LocalDate parseDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateStr, formatter);
        return dateTime.toLocalDate(); // Extract LocalDate from LocalDateTime
    }


}
