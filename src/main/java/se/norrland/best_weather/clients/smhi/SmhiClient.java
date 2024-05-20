package se.norrland.best_weather.clients.smhi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import se.norrland.best_weather.clients.ForecastHandler;
import se.norrland.best_weather.clients.smhi.model.Parameter;
import se.norrland.best_weather.clients.smhi.model.Smhi;
import se.norrland.best_weather.clients.smhi.model.TimeSeries;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SmhiClient implements ForecastHandler<Smhi>{

    private static final String URL = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/18.0300/lat/59.3110/data.json";
    private final WebClient client;

    @Autowired
    public SmhiClient(WebClient.Builder webClientBuilder) {
        this.client = webClientBuilder
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
    public double extractTemperature(Smhi data) {
        return 0;
    }

    @Override
    public double extractHumidity(Smhi data) {
        return 12;
    }

}