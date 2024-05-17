package se.norrland.best_weather.clients.met;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import se.norrland.best_weather.clients.ForecastHandler;
import se.norrland.best_weather.clients.domain.Humidity;
import se.norrland.best_weather.clients.domain.Temperature;
import se.norrland.best_weather.clients.domain.WeatherInfo;
import se.norrland.best_weather.clients.domain.WeatherSrc;
import se.norrland.best_weather.clients.met.model.Met;
import se.norrland.best_weather.clients.met.model.Timeseries;
import se.norrland.best_weather.clients.smhi.model.Parameter;
import se.norrland.best_weather.clients.smhi.model.Smhi;
import se.norrland.best_weather.clients.smhi.model.TimeSeries;

import java.time.LocalDateTime;

import static io.netty.handler.codec.rtsp.RtspHeaders.Values.URL;

@Component
public class MetClient implements ForecastHandler<Met> {

    private static final String GET_URI = "https://api.met.no/weatherapi/locationforecast/2.0/" +
            "compact?lat=59.3110&lon=18.0300";

    private final WebClient client;

    @Autowired
    public MetClient(WebClient.Builder webClientBuilder) {
        this.client = webClientBuilder
                .baseUrl(GET_URI)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public MetClient(WebClient client) {
        this.client = client;
    }

    public Met getMetData() {
        Mono<Met> mono = client
                .get()
                .uri(GET_URI)
                .retrieve()
                .bodyToMono(Met.class);

        return mono.block();
    }

    @Override
    public Temperature extractTemperature(Met met) {
        for (Timeseries timeseries : met.getProperties().getTimeseries()) {
            if (timeseries.getData().getInstant().getDetails().getAdditionalProperties().containsKey("air_temperature")) {
                Double temperatureValue = timeseries.getData().getInstant().getDetails().getAirTemperature();
                return new Temperature(temperatureValue);
            }
        }
        return null;
    }

    @Override
    public Humidity extractHumidity(Met met) {
        return null;
    }

    @Override
    public WeatherInfo makeForecast() {
        Met metData = getMetData();
        if (metData != null) {
            Temperature temperature = extractTemperature(metData);
            Humidity humidity = extractHumidity(metData);
            if (temperature != null && humidity != null) {
                return new WeatherInfo(WeatherSrc.SMHI, temperature, humidity, LocalDateTime.now());
            }
        }
        return null;
    }
}
