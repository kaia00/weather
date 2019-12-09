package com.palm.weather.weatherapp.mapper;

import com.palm.weather.weatherapp.domain.WeatherDetail;
import com.palm.weather.weatherapp.domain.WeatherDomain;
import com.palm.weather.weatherapp.domain.Wind;
import com.palm.weather.weatherapp.model.Weather;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeatherMapperTest {

    private WeatherMapper weatherMapper = new WeatherMapper();

    private static final double TEMPERATURE = 55.6;
    private static final double WIND_SPEED = 10.1;
    private final static double HUMIDITY = 44.4;

    @Test
    public void mapDomainToModel() {
        WeatherDomain weatherDomain = new WeatherDomain();
        WeatherDetail main = new WeatherDetail();
        main.setTemp(TEMPERATURE);
        main.setHumidity(HUMIDITY);

        Wind wind = new Wind();
        wind.setSpeed(WIND_SPEED);

        weatherDomain.setMain(main);
        weatherDomain.setWind(wind);

        Weather weather = weatherMapper.map(weatherDomain);

        assertEquals(weather.getHumidity(), HUMIDITY);
        assertEquals(weather.getTemperature(), TEMPERATURE);
        assertEquals(weather.getWindSpeed(), WIND_SPEED);
    }

}