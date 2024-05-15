package se.norrland.best_weather.clients.smhi;

import se.norrland.best_weather.clients.domain.Humidity;
import se.norrland.best_weather.clients.domain.Temperature;
import se.norrland.best_weather.clients.domain.WeatherInfo;
import se.norrland.best_weather.clients.ForecastHandler;

public class SmhiClient implements ForecastHandler {


    private static final String URL = "https://opendata-download-metfcst.smhi.se/";
    private static final String GET_GEO = "/api/category/pmp3g/version/2/geotype/point/lon" +
            "/18.0300/lat/59.3110/data.json";

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
