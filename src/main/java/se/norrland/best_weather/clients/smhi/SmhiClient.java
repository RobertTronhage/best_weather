package se.norrland.best_weather.clients.smhi;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import se.norrland.best_weather.clients.domain.Humidity;
import se.norrland.best_weather.clients.domain.Temperature;
import se.norrland.best_weather.clients.domain.WeatherInfo;
import se.norrland.best_weather.clients.domain.WeatherSrc;
import se.norrland.best_weather.clients.ForecastHandler;
import se.norrland.best_weather.clients.smhi.model.Parameter;
import se.norrland.best_weather.clients.smhi.model.TimeSeries;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SmhiClient implements ForecastHandler {

    private static final String URL = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/18.0300/lat/59.3110/data.json";
    private final WebClient client;

    public SmhiClient(WebClient.Builder webClientBuilder) {
        this.client = webClientBuilder.baseUrl(URL).build();
    }

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
        WeatherInfo weatherInfo = fetchWeatherInfo();
        if (weatherInfo != null) {
            Temperature temperature = extractTemperature(weatherInfo);
            Humidity humidity = extractHumidity(weatherInfo);
            if (temperature != null && humidity != null) {
                return new WeatherInfo(WeatherSrc.SMHI, temperature, humidity, LocalDateTime.now());
            }
        }
        return null;
    }

    private WeatherInfo fetchWeatherInfo() {
        return client.get()
                .retrieve()
                .bodyToMono(WeatherInfo.class)
                .block();
    }

    private Temperature extractTemperature(WeatherInfo weatherInfo) {
        for (TimeSeries timeSeries : weatherInfo.timeSeries()) {
            for (Parameter parameter : timeSeries.parameters()) {
                if ("t".equals(parameter.name())) {
                    Double temperatureValue = parameter.values().get(0);
                    return new Temperature(temperatureValue);
                }
            }
        }
        return null;
    }

    private Humidity extractHumidity(WeatherInfo weatherInfo) {
        for (TimeSeries timeSeries : weatherInfo.timeSeries()) {
            for (Parameter parameter : timeSeries.parameters()) {
                if ("r".equals(parameter.name())) {
                    Double humidityValue = parameter.values().get(0);
                    return new Humidity(humidityValue, "percent");
                }
            }
        }
        return null;
    }
}