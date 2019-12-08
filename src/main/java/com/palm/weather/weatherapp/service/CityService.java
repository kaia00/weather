package com.palm.weather.weatherapp.service;

import com.palm.weather.weatherapp.exception.IdNotFoundException;
import com.palm.weather.weatherapp.model.City;

import java.util.List;
import java.util.Optional;

public interface CityService {

    City add(City city);

    List<City> findAll();

    City findById(Long id) throws IdNotFoundException;

    Optional<City> findByName(String name);

    City update(Long id, City city) throws IdNotFoundException;

    String delete(Long id) throws IdNotFoundException;

}
