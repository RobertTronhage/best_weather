package se.norrland.best_weather.clients.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import se.norrland.best_weather.clients.smhi.model.TimeSeries;

import java.time.LocalDateTime;

public record WeatherInfo(
        WeatherSrc src,
        Temperature temperature,
        Humidity humidity,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
        LocalDateTime validTime)
        implements Comparable<WeatherInfo> {
    @Override
    public int compareTo(WeatherInfo info) {
        return temperature.compareTo(info.temperature());
    }

    public TimeSeries[] getTimeSeries() {
        return null;
    }
}