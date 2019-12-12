package com.palm.weather.weatherapp.job;

import com.palm.weather.weatherapp.model.City;
import com.palm.weather.weatherapp.model.Weather;
import com.palm.weather.weatherapp.service.CityService;
import com.palm.weather.weatherapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class WeatherCron {

    private final CityService cityService;
    private final WeatherService weatherService;

    @Autowired
    public WeatherCron(CityService cityService, WeatherService weatherService) {
        this.cityService = cityService;
        this.weatherService = weatherService;
    }

    // 15 * 60 * 1000
    @Scheduled(fixedRate = 900000)
    public void getScheduledWeather() {

        //  Number of threads = Number of Available Cores * (1 + Wait time / Service time)
        ExecutorService executorService = Executors.newFixedThreadPool(44);

        List<City> cities = cityService.findAll();

        for (City city : cities) {
            if (city.isAvailable()) {
                executorService.execute(() -> {
                    try {
                        Weather weather = weatherService.fetch(city.getName());
                        if (weather != null) {
                            weather.setCityId(city.getId());
                            weatherService.add(weather);
                        }
                    } catch (RestClientException e) {
                        System.err.println(e.getMessage());
                    }
                });
            }
        }
    }
}
