package com.palm.weather.weatherapp.mapper;

import com.palm.weather.weatherapp.dto.CityDTO;
import com.palm.weather.weatherapp.model.City;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CityMapperTest {

    private CityMapper cityMapper = new CityMapper();

    private static final String CITY_NAME = "Tallinn";

    @Test
    public void mapDTOtoModel(){

        CityDTO cityDTO = new CityDTO();
        cityDTO.setName("Tallinn");

        City city = cityMapper.map(cityDTO);

        assertEquals(city.getName(), CITY_NAME);

    }
}