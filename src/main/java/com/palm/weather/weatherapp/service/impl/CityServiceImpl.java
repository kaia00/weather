package com.palm.weather.weatherapp.service.impl;

import com.palm.weather.weatherapp.exception.IdNotFoundException;
import com.palm.weather.weatherapp.model.City;
import com.palm.weather.weatherapp.model.Weather;
import com.palm.weather.weatherapp.repository.CityRepository;
import com.palm.weather.weatherapp.service.CityService;
import com.palm.weather.weatherapp.service.WeatherService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final WeatherService weatherService;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, WeatherService weatherService) {
        this.cityRepository = cityRepository;
        this.weatherService = weatherService;
    }

    @Override
    public City add(City city) throws IllegalArgumentException {
        String cityName = city.getName().trim();
        city.setName(cityName);
        if (!cityName.isEmpty() && cityName.matches("^([a-zA-Z\\u0080-\\u024F]+(?:. |-| |'))*[a-zA-Z\\u0080-\\u024F]*$")) {
            City addedCity = cityRepository.save(city);
            fetchWeatherAndSave(addedCity);
            return addedCity;
        }
        throw new IllegalArgumentException("Please enter valid city name");
    }

    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    @Override
    public City findById(Long id) throws IdNotFoundException {
        Optional<City> city = cityRepository.findById(id);

        if (city.isPresent()) {
            return city.get();
        }
        throw new IdNotFoundException("No city with id: " + id + " was found!");
    }

    @Override
    public City findByName(String name) throws NotFoundException {
        Optional<City> city = cityRepository.findByName(name);

        if (city.isPresent()) {
            return city.get();
        }
        throw new NotFoundException("No city with name: " + name + " was found!");
    }

    @Override
    public String delete(Long id) throws IdNotFoundException {
        Optional<City> city = cityRepository.findById(id);

        if (city.isPresent()) {
            cityRepository.delete(city.get());
            return "City: " + city.get().getName() + " deleted successfully!";
        }
        throw new IdNotFoundException("No city with id: " + id + " was found!");
    }

    private void fetchWeatherAndSave(City city) {
        ((Runnable) () -> {
            try {
                Weather weather = weatherService.fetch(city.getName());
                if (weather != null) {
                    weather.setCityId(city.getId());
                    weatherService.add(weather);
                }
            } catch (RestClientException e) {
                if (e instanceof HttpClientErrorException &&
                        ((HttpClientErrorException) e).getStatusCode() == HttpStatus.NOT_FOUND) {
                    city.setAvailable(false);
                    cityRepository.save(city);
                } else {
                    e.printStackTrace();
                }
            }
        }).run();
    }
}
