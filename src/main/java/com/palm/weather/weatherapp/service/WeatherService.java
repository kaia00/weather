package com.palm.weather.weatherapp.service;

import com.palm.weather.weatherapp.model.Weather;
import org.springframework.web.client.RestClientException;

public interface WeatherService {

    Weather add(Weather weather);

    Weather fetch(String cityName) throws RestClientException;
}
