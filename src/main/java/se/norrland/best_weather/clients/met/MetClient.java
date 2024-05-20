package se.norrland.best_weather.clients.met;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import se.norrland.best_weather.clients.ForecastHandler;
import se.norrland.best_weather.clients.met.model.Details;
import se.norrland.best_weather.clients.met.model.Met;
import se.norrland.best_weather.clients.met.model.Timeseries;
import se.norrland.best_weather.clients.met.model.Units;
import se.norrland.best_weather.clients.smhi.SmhiData;
import se.norrland.best_weather.clients.smhi.model.Parameter;
import se.norrland.best_weather.clients.smhi.model.Smhi;
import se.norrland.best_weather.clients.smhi.model.TimeSeries;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;


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

    public MetData getMetData() {
        Mono<Met> mono = client
                .get()
                .uri(GET_URI)
                .retrieve()
                .bodyToMono(Met.class);

        Met met = mono.block();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime in24Hours = now.plusHours(24);

        Timeseries closestTimeSeries = null;
        long minDifference = Long.MAX_VALUE;

        for (Timeseries t : met.getProperties().getTimeseries()) {
            try {
                LocalDateTime time = LocalDateTime.parse(t.getTime(), java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME);
                long difference = Math.abs(time.until(in24Hours, ChronoUnit.SECONDS));
                if (difference < minDifference) {
                    minDifference = difference;
                    closestTimeSeries = t;
                }
            } catch (DateTimeParseException e) {
                System.err.println("Error parsing date time: " + t.getTime());
            }
        }

        if (closestTimeSeries == null) {
            System.out.println("No measurement found close to 24 hours from now.");
            return null;
        }

        double temp = closestTimeSeries.getData().getInstant().getDetails().getAirTemperature();
        double humidity = closestTimeSeries.getData().getInstant().getDetails().getRelativeHumidity();

        MetData metData = new MetData();
        LocalDateTime time = LocalDateTime.parse(closestTimeSeries.getTime(), java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME);

        metData.setHumidity(humidity);
        metData.setValidTime(time);
        metData.setTemperature(temp);

        return metData;
    }

    @Override
    public double extractTemperature(Met met) {
        for (Timeseries timeseries : met.getProperties().getTimeseries()) {
            if (timeseries.getData().getInstant().getDetails().getAdditionalProperties().containsKey("air_temperature")) {
                Double temperatureValue = timeseries.getData().getInstant().getDetails().getAirTemperature();
                return temperatureValue;
            }
        }
        return 12;
    }

    // exempel från GPT40, makes any sence?
//    @Override
//    public Temperature extractTemperature(Met met) {
//        for (Timeseries timeseries : met.getProperties().getTimeseries()) {
//            Details details = timeseries.getData().getInstant().getDetails();
//            if (details.getAdditionalProperties().containsKey("air_temperature")) {
//                Double temperatureValue = details.getAirTemperature();
//                return new Temperature(temperatureValue);
//            }
//        }
//        return null;
//    }

    @Override
    public double extractHumidity(Met met) {
        for (Timeseries timeseries : met.getProperties().getTimeseries()) {
            Details details = timeseries.getData().getInstant().getDetails();

            if (details.getAdditionalProperties().containsKey("relative_humidity")) {
                Double humidityValue = details.getRelativeHumidity();
                Units units = met.getProperties().getMeta().getUnits();
                String humidityUnit = units.getRelativeHumidity();

                return humidityValue;
            }
        }
        return 12;
    }
}
