package se.norrland.best_weather.clients.smhi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import se.norrland.best_weather.clients.smhi.model.Parameter;
import se.norrland.best_weather.clients.smhi.model.Smhi;
import se.norrland.best_weather.clients.smhi.model.TimeSeries;
import java.time.format.DateTimeFormatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

@Component
public class SmhiClient {

    private static final String GET_URI = "https://opendata-download-metfcst.smhi.se/api/category/" +
            "pmp3g/version/2/geotype/point/lon/18.0300/lat/59.3110/data.json";

    private final WebClient client;

    @Autowired
    public SmhiClient(WebClient.Builder webClientBuilder) {
        this.client = webClientBuilder
                .baseUrl(GET_URI)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public SmhiData getSmhiData() {
        Mono<Smhi> mono = client
                .get()
                .uri(GET_URI)
                .retrieve()
                .bodyToMono(Smhi.class);

        Smhi smhi = mono.block();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime in24Hours = now.plusHours(24);

        TimeSeries closestTimeSeries = null;
        long minDifference = Long.MAX_VALUE;

        for (TimeSeries t : smhi.getTimeSeries()) {
            try {
                LocalDateTime time = LocalDateTime.parse(t.getValidTime(), DateTimeFormatter.ISO_ZONED_DATE_TIME);
                long difference = Math.abs(time.until(in24Hours, ChronoUnit.SECONDS));
                if (difference < minDifference) {
                    minDifference = difference;
                    closestTimeSeries = t;
                }
            } catch (DateTimeParseException e) {
                System.err.println("Error parsing date time: " + t.getValidTime());
            }
        }

        if (closestTimeSeries == null) {
            System.out.println("No measurement found close to 24 hours from now.");
            return null;
        }

        double temp = 0;
        double humidity = 0;

        for (Parameter parameter : closestTimeSeries.getParameters()) {
            if ("t".equals(parameter.getName())) {
                temp = parameter.getValues().get(0);
            } else if ("r".equals(parameter.getName())) {
                humidity = parameter.getValues().get(0);
            }
        }

        SmhiData smhiData = new SmhiData();
        LocalDateTime time = LocalDateTime.parse(closestTimeSeries.getValidTime(), java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME);

        smhiData.setHumidity(humidity);
        smhiData.setValidTime(time);
        smhiData.setTemperature(temp);

        return smhiData;
    }
}
