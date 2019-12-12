package com.palm.weather.weatherapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.palm.weather.weatherapp.dto.CityDTO;
import com.palm.weather.weatherapp.mapper.CityMapper;
import com.palm.weather.weatherapp.model.City;
import com.palm.weather.weatherapp.service.CityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CityController.class)
public class CityControllerTest {

    @MockBean
    CityController cityController;

    @MockBean
    CityService cityService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;


    @Test
    public void testAddCityReturnHttpStatusCreated() throws Exception {

        CityDTO cityDTO = new CityDTO("Tallinn");

        City city = new City("Tallinn");

        ResponseEntity responseEntity = new ResponseEntity<>(city, HttpStatus.CREATED);

        given(cityController.addCity(cityDTO)).willReturn(responseEntity);

        String json = mapper.writeValueAsString(city);

        mockMvc.perform(post("/cities")
                .contentType((MediaType.APPLICATION_JSON))
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
