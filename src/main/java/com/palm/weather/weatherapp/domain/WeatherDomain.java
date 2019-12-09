package com.palm.weather.weatherapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDomain {

    private WeatherDetail main;
    private Wind wind;

    public WeatherDetail getMain() {
        return main;
    }

    public void setMain(WeatherDetail main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}
