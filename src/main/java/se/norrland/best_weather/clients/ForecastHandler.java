package se.norrland.best_weather.clients;


import se.norrland.best_weather.clients.smhi.model.Smhi;

public interface ForecastHandler<T> {

    double extractTemperature(T data);

    double extractHumidity(T data);
}
