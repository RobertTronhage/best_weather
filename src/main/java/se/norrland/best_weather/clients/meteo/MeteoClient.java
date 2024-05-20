package se.norrland.best_weather.clients.meteo;

import org.springframework.web.reactive.function.client.WebClient;
import se.norrland.best_weather.clients.ForecastHandler;
import se.norrland.best_weather.clients.meteo.model.Meteo;

public class MeteoClient implements ForecastHandler<Meteo> {

    private static final String GET_URI = "https://api.open-meteo.com/v1/meteofrance?" +
            "latitude=59.3094&longitude=18.0234&hourly=temperature_2m,relative_humidity_2m&forecast_days=3";

    private final WebClient client;

    public MeteoClient(WebClient client) {
        this.client = client;
    }

    @Override
    public double extractTemperature(Meteo data) {
        return 12;
    }

    @Override
    public double extractHumidity(Meteo data) {
        return 12;
    }


}
