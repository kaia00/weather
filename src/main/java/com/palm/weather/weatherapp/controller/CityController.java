package com.palm.weather.weatherapp.controller;

import com.palm.weather.weatherapp.exception.IdNotFoundException;
import com.palm.weather.weatherapp.model.City;
import com.palm.weather.weatherapp.service.CityService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("cities")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping
    public ResponseEntity addCity(@RequestBody City city) {
        try {
            City existingCity = cityService.findByName(city.getName());

            return new ResponseEntity<>(existingCity, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(cityService.add(city), HttpStatus.CREATED);
        }
    }

    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(cityService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) throws IdNotFoundException {
        return new ResponseEntity<>(cityService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) throws IdNotFoundException {
        return new ResponseEntity<>(cityService.delete(id), HttpStatus.OK);
    }
}
