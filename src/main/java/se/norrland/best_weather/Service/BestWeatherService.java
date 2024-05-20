package se.norrland.best_weather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.norrland.best_weather.clients.ForecastHandler;
import se.norrland.best_weather.service.BestWeather;
import se.norrland.best_weather.clients.smhi.SmhiClient;
import se.norrland.best_weather.clients.smhi.model.Smhi;

import java.util.List;

@Service
public class BestWeatherService {

    private final List<ForecastHandler> forecastHandlers;

    private final SmhiClient smhiClient;

    @Autowired
    public BestWeatherService(List<ForecastHandler> forecastHandlers, SmhiClient smhiClient) {
        this.forecastHandlers = forecastHandlers;
        this.smhiClient = smhiClient;
    }

    // Get weather forecasts from all available forecast handlers
    // and return the best one using if-else statements

    public BestWeather getBestWeather() {

        Smhi smhi = smhiClient.getSmhiData();
        double smhiTemp = smhiClient.extractTemperature(smhi);
        double humidity = smhiClient.extractHumidity(smhi);

//        BestWeather bestWeather = calcHighestTemp(smhiTemp)

        return null;
    }

    public double calcHighestTemp(double smhi,double MET, double METEO){

        if (smhi > MET && smhi > METEO){
            return smhi;
        }else if (MET > smhi && MET > METEO){
            return MET;
        }else {
            return METEO;
        }
    }
}