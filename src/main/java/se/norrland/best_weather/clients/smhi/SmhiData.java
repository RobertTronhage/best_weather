package se.norrland.best_weather.clients.smhi;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import se.norrland.best_weather.clients.WeatherData;

import java.time.LocalDateTime;

@Component
@Qualifier("smhiData")
public class SmhiData implements WeatherData {

    private String provider = "Smhi";
    private double temperature;
    private double humidity;
    private LocalDateTime validTime;
    public SmhiData() {
    }

    public SmhiData(String provider, double temperature, double humidity, LocalDateTime validTime) {
        this.provider = provider;
        this.temperature = temperature;
        this.humidity = humidity;
        this.validTime = validTime;
    }

    @Override
    public String getProvider() {
        return provider;
    }

    @Override
    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    @Override
    public LocalDateTime getValidTime() {
        return validTime;
    }

    public void setValidTime(LocalDateTime validTime) {
        this.validTime = validTime;
    }

    @Override
    public String toString() {
        return "{" +
                "provider='" + provider + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                " %, validTime=" + validTime +
                '}';
    }
}
