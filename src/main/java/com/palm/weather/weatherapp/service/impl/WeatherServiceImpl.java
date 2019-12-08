package com.palm.weather.weatherapp.service.impl;

import com.palm.weather.weatherapp.model.Weather;
import com.palm.weather.weatherapp.repository.WeatherRepository;
import com.palm.weather.weatherapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final WeatherRepository weatherRepository;

    @Autowired
    public WeatherServiceImpl(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Override
    public Weather add(Weather weather) {
        return weatherRepository.save(weather);
    }
}
