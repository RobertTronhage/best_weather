package se.norrland.best_weather.clients.smhi;

import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientSsl;
import org.springframework.stereotype.Component;
import se.norrland.best_weather.clients.domain.Humidity;
import se.norrland.best_weather.clients.domain.Temperature;
import org.springframework.web.reactive.function.client.WebClient;
import se.norrland.best_weather.clients.domain.WeatherInfo;
import se.norrland.best_weather.clients.ForecastHandler;

@Component
public class SmhiClient implements ForecastHandler {


    private static final String URL = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/18.0300/lat/59.3110/data.json";

    WebClient client = WebClient.create(URL);

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
        WeatherInfo weatherInfo = client.get()
                .retrieve()
                .bodyToMono(WeatherInfo.class)
                .block();

        return weatherInfo;
    }
}