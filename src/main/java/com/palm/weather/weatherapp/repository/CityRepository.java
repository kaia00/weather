package com.palm.weather.weatherapp.repository;

import com.palm.weather.weatherapp.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("SELECT city from City city where lower(city.name)=lower(:name)")
    Optional<City> findByName(@Param("name") String name);

}
