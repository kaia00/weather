package com.palm.weather.weatherapp.service.impl;

import com.palm.weather.weatherapp.exception.IdNotFoundException;
import com.palm.weather.weatherapp.model.City;
import com.palm.weather.weatherapp.repository.CityRepository;
import com.palm.weather.weatherapp.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public City add(City city) {
        return cityRepository.save(city);
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
    public Optional<City> findByName(String name) {
        return cityRepository.findByName(name);
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
}
