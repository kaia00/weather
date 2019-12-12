package com.palm.weather.weatherapp.controller;

import com.palm.weather.weatherapp.dto.CityDTO;
import com.palm.weather.weatherapp.exception.IdNotFoundException;
import com.palm.weather.weatherapp.mapper.CityMapper;
import com.palm.weather.weatherapp.model.City;
import com.palm.weather.weatherapp.service.CityService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://ilma-info.herokuapp.com")
@RestController
@RequestMapping("cities")
public class CityController {

    private final CityService cityService;
    private final CityMapper cityMapper;

    @Autowired
    public CityController(CityService cityService, CityMapper cityMapper) {
        this.cityService = cityService;
        this.cityMapper = cityMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity addCity(@RequestBody CityDTO cityDTO) {
        try {
            City existingCity = cityService.findByName(cityDTO.getName().trim());
            return new ResponseEntity<>(existingCity, HttpStatus.OK);
        } catch (NotFoundException e) {
            try {
                City newCity = cityService.add(cityMapper.map(cityDTO));
                return new ResponseEntity<>(newCity, HttpStatus.CREATED);
            } catch (IllegalArgumentException ex) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
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
