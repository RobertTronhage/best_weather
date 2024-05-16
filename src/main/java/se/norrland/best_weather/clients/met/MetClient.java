package se.norrland.best_weather.clients.met;

import se.norrland.best_weather.clients.ForecastHandler;
import se.norrland.best_weather.clients.domain.Humidity;
import se.norrland.best_weather.clients.domain.Temperature;
import se.norrland.best_weather.clients.domain.WeatherInfo;
import se.norrland.best_weather.clients.met.model.Met;

public class MetClient implements ForecastHandler<Met> {

    private static final String GET_URI = "https://api.met.no/weatherapi/locationforecast/2.0/" +
            "compact?lat=59.3110&lon=18.0300";


    @Override
    public Temperature extractTemperature(Met met) {
        return null;
    }

    @Override
    public Humidity extractHumidity(Met met) {
        return null;
    }

    @Override
    public WeatherInfo makeForecast() {
        return null;
    }
}
