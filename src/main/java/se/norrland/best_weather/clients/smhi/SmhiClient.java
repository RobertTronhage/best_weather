package se.norrland.best_weather.clients.smhi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import se.norrland.best_weather.clients.domain.Humidity;
import se.norrland.best_weather.clients.domain.Temperature;
import se.norrland.best_weather.clients.domain.WeatherInfo;
import se.norrland.best_weather.clients.domain.WeatherSrc;
import se.norrland.best_weather.clients.ForecastHandler;
import se.norrland.best_weather.clients.smhi.model.Parameter;
import se.norrland.best_weather.clients.smhi.model.Smhi;
import se.norrland.best_weather.clients.smhi.model.TimeSeries;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SmhiClient implements ForecastHandler {

    private static final String URL = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/18.0300/lat/59.3110/data.json";
    private final WebClient client;

    Smhi smhi = new Smhi();

    public SmhiClient(WebClient.Builder webClientBuilder) {
        this.client = webClientBuilder.baseUrl(URL).build();
    }

    public SmhiClient() {
        client = WebClient.builder()
                .baseUrl(URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Smhi getSmhiData() {
        Mono<Smhi> mono = client
                .get()
                .uri(URL)
                .retrieve()
                .bodyToMono(Smhi.class);

        return mono.block();
    }

    @Override
    public WeatherInfo makeForecast() {
//        WeatherInfo weatherInfo = fetchWeatherInfo();
//        if (weatherInfo != null) {
//            Temperature temperature = extractTemperature(weatherInfo);
//            Humidity humidity = extractHumidity(weatherInfo);
//            if (temperature != null && humidity != null) {
//                return new WeatherInfo(WeatherSrc.SMHI, temperature, humidity, LocalDateTime.now());
//            }
//        }
        return null;
    }

    public Temperature extractTemperature() {
        smhi = getSmhiData();

        for (TimeSeries timeSeries : smhi.getTimeSeries()) {
            for (Parameter parameter : timeSeries.getParameters()) {
                if ("t".equals(parameter.getName())) {
                    Double temperatureValue = Double.valueOf(parameter.getValues().get(0));
                    return new Temperature(temperatureValue);
                }
            }
        }
        return null;
    }

    public Humidity extractHumidity() {
        for (TimeSeries timeSeries : smhi.getTimeSeries()) {
            for (Parameter parameter : timeSeries.getParameters()) {
                if ("r".equals(parameter.getName())) {
                    Double humidityValue = Double.valueOf(parameter.getValues().get(0));
                    return new Humidity(humidityValue, "percent");
                }
            }
        }
        return null;
    }
}