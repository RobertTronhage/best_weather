package se.norrland.best_weather.clients.meteo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import se.norrland.best_weather.clients.meteo.model.Meteo;
import se.norrland.best_weather.clients.smhi.model.TimeSeries;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class MeteoClient {

    private static final String GET_URI = "https://api.open-meteo.com/v1/forecast?" +
            "latitude=59.3094&longitude=18.0234&hourly=temperature_2m,relative_humidity_2m&forecast_days=3";

    private final WebClient client;

    @Autowired
    public MeteoClient(WebClient.Builder webClientBuilder) {
        this.client = webClientBuilder
                .baseUrl(GET_URI)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public MeteoData getMeteoData() {
        Mono<Meteo> mono = client
                .get()
                .uri(GET_URI)
                .retrieve()
                .bodyToMono(Meteo.class);

        Meteo meteo = mono.block();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime in24Hours = now.plusHours(24);
        double temperature = 0;

        TimeSeries closestTimeSeries = null;
        long minDifference = Long.MAX_VALUE;

        MeteoData meteoData = new MeteoData();

        if (meteo != null && meteo.getHourly() != null) {
            List<String> times = meteo.getHourly().getTime();
            List<Integer> temperatures = meteo.getHourly().getTemperature2m();
            List<Integer> humidities = meteo.getHourly().getRelativeHumidity2m();

            if (times != null && !times.isEmpty() && temperatures != null && !temperatures.isEmpty() && humidities != null && !humidities.isEmpty()) {

                for (int i = 0; i < times.size(); i++) {
                    LocalDateTime time = LocalDateTime.parse(times.get(i), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    if (!time.isBefore(in24Hours)) {
                        temperature = (double) temperatures.get(i);
                        meteoData.setValidTime(in24Hours);
                        meteoData.setHumidity((double) humidities.get(i));
                        meteoData.setTemperature(temperature);
                        break;
                    }
                }
            } else {
                System.out.println("No current weather data available.");
            }
        } else {
            System.out.println("No current weather data available.");
        }
        return meteoData;
    }
}
