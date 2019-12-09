package com.palm.weather.weatherapp.job;

import com.palm.weather.weatherapp.domain.WeatherDomain;
import com.palm.weather.weatherapp.mapper.WeatherMapper;
import com.palm.weather.weatherapp.model.City;
import com.palm.weather.weatherapp.model.Weather;
import com.palm.weather.weatherapp.service.CityService;
import com.palm.weather.weatherapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class WeatherCron {

    private final CityService cityService;
    private final WeatherService weatherService;
    private final WebClient.Builder webClientBuilder;
    private final WeatherMapper weatherMapper;

    @Autowired
    public WeatherCron(CityService cityService, WeatherService weatherService, WebClient.Builder webClientBuilder,
                       WeatherMapper weatherMapper) {
        this.cityService = cityService;
        this.weatherService = weatherService;
        this.webClientBuilder = webClientBuilder;
        this.weatherMapper = weatherMapper;
    }

    @Scheduled(fixedRate = 5000)
    public void getScheduledWeather() {

//        List<City> cities = cityService.findAll();
//
//        for (City city : cities) {
//            WeatherDomain weatherDomain = webClientBuilder.build()
//                    .get()
//                    .uri("http://api.openweathermap.org/data/2.5/weather?APPID=b5b5be990915bf18f851f86d73398368&units=metric&q="
//                            + city.getName())
//                    .accept(MediaType.APPLICATION_JSON)
//                    .retrieve()
//                    .bodyToMono(WeatherDomain.class)
//                    .block();
//
//            if (weatherDomain != null) {
//                Weather weather = weatherMapper.map(weatherDomain);
//                weather.setCityId(city.getId());
//                weatherService.add(weather);
//            }
//        }


        //  Number of threads = Number of Available Cores * (1 + Wait time / Service time)
        ExecutorService executorService = Executors.newFixedThreadPool(44);

        List<City> cities = cityService.findAll();

        for (City city : cities) {
            executorService.execute(() -> {
                final String uri = getUri(city);

                RestTemplate restTemplate = new RestTemplate();

                try {
                    WeatherDomain weatherResponse = restTemplate.getForObject(uri, WeatherDomain.class);
                    if (weatherResponse != null) {
                        Weather weather = weatherMapper.map(weatherResponse);
                        weather.setCityId(city.getId());
                        weather.setDate(LocalDateTime.now());

                        weatherService.add(weather);
                        System.out.println("Temp: " + weather.getTemperature() + " Humidity: " + weather.getHumidity() +
                                " Windspeed: " + weather.getWindSpeed());
                    }
                } catch (RestClientException e) {
                    System.err.println(e.getMessage());
                }

            });
        }
    }

    private String getUri(City city) {
        return "http://api.openweathermap.org/data/2.5/weather?APPID=b5b5be990915bf18f851f86d73398368&units=metric&q=" +
                city.getName();
    }
}
