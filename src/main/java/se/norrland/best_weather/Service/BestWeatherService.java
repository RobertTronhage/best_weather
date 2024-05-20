package se.norrland.best_weather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.norrland.best_weather.clients.WeatherData;
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

    //    public BestWeather getBestWeather() {
//
//        BestWeather bestWeather = new BestWeather();
//
//        SmhiData smhi = smhiClient.getSmhiData();
//        MetData met = metClient.getMetData();
//        MeteoData meteo = meteoClient.getMeteoData();
//
//        double highestTemp = calcHighestTemp(smhi.getTemperature(),met.getTemperature(),meteo.getTemperature());
//
//        if (smhi.getTemperature()==highestTemp){
//            bestWeather.setHumidity(smhi.getHumidity());
//            bestWeather.setTemp(smhi.getTemperature());
//            bestWeather.setOrigin(smhi.getProvider());
//            bestWeather.setTimestamp(smhi.getValidTime().toString());
//        } else if (met.getTemperature() == highestTemp) {
//            bestWeather.setHumidity(met.getHumidity());
//            bestWeather.setTemp(met.getTemperature());
//            bestWeather.setOrigin(met.getProvider());
//            bestWeather.setTimestamp(met.getValidTime().toString());
//        }else {
//            bestWeather.setHumidity(meteo.getHumidity());
//            bestWeather.setTemp(meteo.getTemperature());
//            bestWeather.setOrigin(meteo.getProvider());
//            bestWeather.setTimestamp(meteo.getValidTime().toString());
//        }
//
//        return null;
//    }

    public BestWeather getBestWeather() {
        List<WeatherData> weatherDataList = List.of(
                smhiClient.getSmhiData(),
                metClient.getMetData(),
                meteoClient.getMeteoData()
        );

        WeatherData bestWeatherData = weatherDataList.stream()
                .max(Comparator.comparingDouble(WeatherData::getTemperature))
                .orElseThrow(() -> new RuntimeException("No weather data available"));

        BestWeather bestWeather = new BestWeather();
        bestWeather.setHumidity(bestWeatherData.getHumidity());
        bestWeather.setTemp(bestWeatherData.getTemperature());
        bestWeather.setOrigin(bestWeatherData.getProvider());
        bestWeather.setTimestamp(bestWeatherData.getValidTime().toString());

        return bestWeather;
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