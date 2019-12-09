package com.palm.weather.weatherapp.mapper;

import com.palm.weather.weatherapp.domain.WeatherDomain;
import com.palm.weather.weatherapp.model.Weather;
import org.springframework.stereotype.Component;

@Component
public class WeatherMapper {

    public Weather map(WeatherDomain weatherDomain) {
        Weather weather = new Weather();
        weather.setTemperature(weatherDomain.getMain().getTemp());
        weather.setHumidity(weatherDomain.getMain().getHumidity());
        weather.setWindSpeed(weatherDomain.getWind().getSpeed());

        return weather;
    }
}
