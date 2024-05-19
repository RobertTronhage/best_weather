package se.norrland.best_weather.clients.meteo;

import org.springframework.web.reactive.function.client.WebClient;
import se.norrland.best_weather.clients.ForecastHandler;
import se.norrland.best_weather.clients.domain.Humidity;
import se.norrland.best_weather.clients.domain.Temperature;
import se.norrland.best_weather.clients.domain.WeatherInfo;
import se.norrland.best_weather.clients.meteo.model.Meteo;

public class MeteoClient implements ForecastHandler<Meteo> {

    private static final String GET_URI = "https://api.open-meteo.com/v1/meteofrance?" +
            "latitude=59.3094&longitude=18.0234&hourly=temperature_2m,relative_humidity_2m&forecast_days=3";

    private final WebClient client;

    public MeteoClient(WebClient client) {
        this.client = client;
    }

    @Override
    public Temperature extractTemperature(Meteo data) {
        return null;
    }

    @Override
    public Humidity extractHumidity(Meteo data) {
        return null;
    }

    @Override
    public WeatherInfo makeForecast() {
        return null;
    }
}
