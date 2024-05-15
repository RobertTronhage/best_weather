package se.norrland.best_weather.clients.met;

import se.norrland.best_weather.clients.ForecastHandler;
import se.norrland.best_weather.clients.domain.Humidity;
import se.norrland.best_weather.clients.domain.Temperature;
import se.norrland.best_weather.clients.domain.WeatherInfo;

public class MetClient implements ForecastHandler {

    private static final String GET_URI = "https://api.met.no/weatherapi/locationforecast/2.0/" +
            "compact?lat=59.3110&lon=18.0300";


    @Override
    public Temperature getTemperature() {
        return null;
    }

    @Override
    public Humidity getHumidity() {
        return null;
    }

    @Override
    public WeatherInfo makeForecast() {
        return null;
    }
}
