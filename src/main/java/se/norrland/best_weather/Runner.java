package se.norrland.best_weather;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.norrland.best_weather.Service.BestWeatherService;
import se.norrland.best_weather.clients.WeatherData;
import se.norrland.best_weather.clients.met.MetClient;
import se.norrland.best_weather.clients.met.MetData;
import se.norrland.best_weather.clients.met.model.Met;
import se.norrland.best_weather.clients.meteo.MeteoClient;
import se.norrland.best_weather.clients.meteo.MeteoData;
import se.norrland.best_weather.clients.smhi.SmhiClient;
import se.norrland.best_weather.clients.smhi.SmhiData;
import se.norrland.best_weather.clients.smhi.model.Smhi;

import java.time.LocalDateTime;

@Component
public class Runner implements CommandLineRunner {

    private final SmhiClient client;
    private final MetClient metClient;
    private final MeteoClient meteoClient;
    private final BestWeatherService bestWeatherService;

//    private final WeatherData weatherData;

    public Runner(SmhiClient client, MetClient metClient, MeteoClient meteoClient, BestWeatherService bestWeatherService) {
        this.client = client;
        this.metClient = metClient;
        this.meteoClient = meteoClient;
        this.bestWeatherService = bestWeatherService;
//        this.weatherData = weatherData;
    }

    @Override
    public void run(String... args) {

        SmhiData data = client.getSmhiData();
        System.out.println(data);

        MetData mdata = metClient.getMetData();
        System.out.println(mdata);

        MeteoData meData = meteoClient.getMeteoData();
        System.out.println(meData);

//        double temp = bestWeatherService.calcHighestTemp(data.getTemperature(),mdata.getTemperature(),meData.getTemperature());
//
//        System.out.println(temp);

        System.out.println(bestWeatherService.getBestWeather());


    }
}
