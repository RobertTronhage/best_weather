package se.norrland.best_weather.clients;

import se.norrland.best_weather.clients.domain.Humidity;
import se.norrland.best_weather.clients.domain.Temperature;
import se.norrland.best_weather.clients.domain.WeatherInfo;
import se.norrland.best_weather.clients.smhi.model.Smhi;

public interface ForecastHandler<T> {

    Temperature extractTemperature(T data);

    Humidity extractHumidity(T data);

    WeatherInfo makeForecast();
}
