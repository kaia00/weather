package com.palm.weather.weatherapp.mapper;

import com.palm.weather.weatherapp.dto.CityDTO;
import com.palm.weather.weatherapp.model.City;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {

    public City map(CityDTO cityDTO) {

        City city = new City();
        city.setName(cityDTO.getName());

        return city;
    }
}
