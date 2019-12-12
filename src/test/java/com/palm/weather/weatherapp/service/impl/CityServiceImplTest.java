package com.palm.weather.weatherapp.service.impl;

import com.palm.weather.weatherapp.model.City;
import com.palm.weather.weatherapp.service.CityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
class CityServiceImplTest {

    private static final City correctCity = new City("Tallinn");
    private static final City correctCityWithSpace = new City("New York");
    private static final City correctCityWithDash = new City("New-york");
    private static final City correctCityWithNonLatinSymbols = new City("Võru");
    private static final City correctCityWithEmptySpaces = new City("    Tartu   ");
    private static final City correctCityWithDot = new City("St. Georges");
    private static final City wrongCityWithSymbols = new City("@@@@");
    private static final City wrongCityEmpty = new City("");
    private static final City wrongCityWithNumbers = new City("123123");
    @Autowired
    CityService cityService;

    @Test
    public void testAddCityAddsNormalNameCity() {
        City city = cityService.add(correctCity);
        assertEquals(city.getName(), correctCity.getName());
    }

    @Test
    public void testAddCityAddsCityWithSpace() {
        City city = cityService.add(correctCityWithSpace);
        assertEquals(city.getName(), correctCityWithSpace.getName());
    }

    @Test
    public void testAddCityAddsCityWithDash() {
        City city = cityService.add(correctCityWithDash);
        assertEquals(city.getName(), correctCityWithDash.getName());
    }

    @Test
    public void testAddCityAddsCityWithNonLatinSymbols() {
        City city = cityService.add(correctCityWithNonLatinSymbols);
        assertEquals(city.getName(), correctCityWithNonLatinSymbols.getName());
    }

    @Test
    public void testAddCityAddsCityWithEmptySpaces() {
        City city = cityService.add(correctCityWithEmptySpaces);
        assertEquals(city.getName(), correctCityWithEmptySpaces.getName());
    }

    @Test
    public void testAddCityAddsCityWithDot() {
        City city = cityService.add(correctCityWithDot);
        assertEquals(city.getName(), correctCityWithDot.getName());
    }

    @Test
    public void testAddCityAddsWrongCityWithSymbols() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> cityService.add(wrongCityWithSymbols));
    }

    @Test
    public void testAddCityAddsWrongCityEmpty() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> cityService.add(wrongCityEmpty));
    }

    @Test
    public void testAddCityAddsWrongCityWithNumbers() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> cityService.add(wrongCityWithNumbers));
    }

}