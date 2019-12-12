package com.palm.weather.weatherapp.service.impl;

import com.palm.weather.weatherapp.domain.WeatherDomain;
import com.palm.weather.weatherapp.mapper.WeatherMapper;
import com.palm.weather.weatherapp.model.Weather;
import com.palm.weather.weatherapp.repository.WeatherRepository;
import com.palm.weather.weatherapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final WeatherRepository weatherRepository;
    private final WeatherMapper weatherMapper;

    @Autowired
    public WeatherServiceImpl(WeatherRepository weatherRepository, WeatherMapper weatherMapper) {
        this.weatherRepository = weatherRepository;
        this.weatherMapper = weatherMapper;
    }

    @Override
    public Weather add(Weather weather) {
        return weatherRepository.save(weather);
    }

    @Override
    public Weather fetch(String cityName) throws RestClientException {
        final String uri = getUri(cityName);

        RestTemplate restTemplate = new RestTemplate();

        WeatherDomain weatherResponse = restTemplate.getForObject(uri, WeatherDomain.class);
        if (weatherResponse != null) {
            Weather weather = weatherMapper.map(weatherResponse);
            weather.setDate(LocalDateTime.now());

            return weather;
        }
        return null;
    }

    private String getUri(String cityName) {
        return "http://api.openweathermap.org/data/2.5/weather?APPID=b5b5be990915bf18f851f86d73398368&units=metric&q=" +
                cityName;
    }
}
