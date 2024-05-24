package se.norrland.best_weather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.norrland.best_weather.clients.met.MetClient;
import se.norrland.best_weather.clients.meteo.MeteoClient;
import se.norrland.best_weather.clients.smhi.SmhiClient;

import java.util.Comparator;
import java.util.List;

@Service
public class BestWeatherService {

    private final SmhiClient smhiClient;
    private final MetClient metClient;
    private final MeteoClient meteoClient;

    @Autowired
    public BestWeatherService(SmhiClient smhiClient, MetClient metClient, MeteoClient meteoClient) {
        this.smhiClient = smhiClient;
        this.metClient = metClient;
        this.meteoClient = meteoClient;
    }

    public BestWeather getBestWeather() {
        List<BestWeather> weatherDataList = List.of(
                smhiClient.getSmhiData(),
                metClient.getMetData(),
                meteoClient.getMeteoData()
        );

         BestWeather bestWeatherData = weatherDataList.stream()
                .max(Comparator.comparingDouble(BestWeather::getTemp))
                .orElseThrow(() -> new RuntimeException("No weather data available"));

        return bestWeatherData;
    }
}