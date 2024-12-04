package com.example.demo.client;

import com.sun.tools.javac.Main;

import java.time.LocalDateTime;

public class WeatherListItem {
    private Main main;
    private LocalDateTime dtTxt;


    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public LocalDateTime getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(LocalDateTime dtTxt) {
        this.dtTxt = dtTxt;
    }
}
