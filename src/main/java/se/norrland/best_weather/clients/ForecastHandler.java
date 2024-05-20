package se.norrland.best_weather.clients;


import se.norrland.best_weather.clients.smhi.model.Smhi;
import se.norrland.best_weather.service.BestWeather;

public interface ForecastHandler<T> {

    double extractTemperature(T data);

    double extractHumidity(T data);
}
