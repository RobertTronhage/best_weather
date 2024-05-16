package se.norrland.best_weather.clients;

import se.norrland.best_weather.clients.domain.Humidity;
import se.norrland.best_weather.clients.domain.Temperature;
import se.norrland.best_weather.clients.domain.WeatherInfo;

public interface ForecastHandler {

    Temperature extractTemperature();

    Humidity extractHumidity();

    WeatherInfo makeForecast();
}
