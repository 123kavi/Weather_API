package com.example.demo.client;


import java.util.List;

public class OpenWeatherResponse {
    private List<WeatherListItem> list;

    public List<WeatherListItem> getList() {
        return list;
    }

    public void setList(List<WeatherListItem> list) {
        this.list = list;
    }
}
