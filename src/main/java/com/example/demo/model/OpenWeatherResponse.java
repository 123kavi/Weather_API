package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents the JSON response from the OpenWeatherMap API.
 */
public class OpenWeatherResponse {

    @JsonProperty("list")
    private List<WeatherItem> list;

    public List<WeatherItem> getList() {
        return list;
    }

    public void setList(List<WeatherItem> list) {
        this.list = list;
    }

    /**
     * Represents each weather item in the response.
     */
    public static class WeatherItem {

        @JsonProperty("dt_txt")
        private String dtTxt;

        @JsonProperty("main")
        private Main main;


        public String getDtTxt() {
            return dtTxt;
        }

        public void setDtTxt(String dtTxt) {
            this.dtTxt = dtTxt;
        }


        public Main getMain() {
            return main;
        }

        public void setMain(Main main) {
            this.main = main;
        }
    }


    public static class Main {

        @JsonProperty("temp")
        private double temp;


        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }
    }
}
