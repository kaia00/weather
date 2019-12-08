package com.palm.weather.weatherapp.service.impl;

import com.palm.weather.weatherapp.Exception.IdNotFoundException;
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
    public City update(Long id, City city) throws IdNotFoundException {
        Optional<City> cityToUpdate = cityRepository.findById(id);

        if (cityToUpdate.isPresent()) {
            City updatedCity = cityToUpdate.get();
            updatedCity.setName(city.getName());
            return cityRepository.save(updatedCity);
        }
        throw new IdNotFoundException("No city with id: " + id + " was found!");
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
