package se.norrland.best_weather.clients;

import java.time.LocalDateTime;

public interface WeatherData {

        double getTemperature();
        double getHumidity();
        String getProvider();
        LocalDateTime getValidTime();

}
