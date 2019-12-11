package com.palm.weather.weatherapp.service;

import com.palm.weather.weatherapp.exception.IdNotFoundException;
import com.palm.weather.weatherapp.model.City;
import javassist.NotFoundException;

import java.util.List;

public interface CityService {

    City add(City city) throws IllegalArgumentException;

    List<City> findAll();

    City findById(Long id) throws IdNotFoundException;

    City findByName(String name) throws NotFoundException;

    String delete(Long id) throws IdNotFoundException;

}
