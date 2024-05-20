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
import se.norrland.best_weather.clients.met.model.Details;
import se.norrland.best_weather.clients.met.model.Met;
import se.norrland.best_weather.clients.met.model.Timeseries;
import se.norrland.best_weather.clients.met.model.Units;
import java.time.LocalDateTime;



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
