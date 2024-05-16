package se.norrland.best_weather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.norrland.best_weather.clients.ForecastHandler;
import se.norrland.best_weather.clients.domain.WeatherInfo;

import java.util.List;

@Service
public class BestWeatherService {

    private final List<ForecastHandler> forecastHandlers;

    @Autowired
    public BestWeatherService(List<ForecastHandler> forecastHandlers) {
        this.forecastHandlers = forecastHandlers;
    }

    public WeatherInfo getBestWeather() {
        WeatherInfo bestWeather = null;

        for (ForecastHandler handler : forecastHandlers) {
            WeatherInfo forecast = handler.makeForecast();
            if (forecast != null) {
                if (bestWeather == null || forecast.compareTo(bestWeather) > 0) {
                    bestWeather = forecast;
                }
            }
        }

        return bestWeather;
    }
}