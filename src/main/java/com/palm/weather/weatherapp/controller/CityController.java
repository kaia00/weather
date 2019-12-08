package com.palm.weather.weatherapp.controller;

import com.palm.weather.weatherapp.Exception.IdNotFoundException;
import com.palm.weather.weatherapp.model.City;
import com.palm.weather.weatherapp.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @PostMapping
    public ResponseEntity addCity(@RequestBody City city) {
        Optional<City> existingCity = cityService.findByName(city.getName());

        if (existingCity.isPresent()) {
            return new ResponseEntity<>(existingCity.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(cityService.add(city), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(cityService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) throws IdNotFoundException {
        return new ResponseEntity<>(cityService.findById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody City city) throws IdNotFoundException {
        return new ResponseEntity<>(cityService.update(id, city), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) throws IdNotFoundException {
        return new ResponseEntity<>(cityService.delete(id), HttpStatus.OK);
    }
}
