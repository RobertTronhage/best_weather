package se.norrland.best_weather.clients.met;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MetData {

    private String provider = "Met";
    private double temperature;
    private double humidity;
    private LocalDateTime validTime;

    public MetData() {
    }

    public MetData(String provider, double temperature, double humidity, LocalDateTime validTime) {
        this.provider = provider;
        this.temperature = temperature;
        this.humidity = humidity;
        this.validTime = validTime;
    }

    public String getProvider() {
        return provider;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

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
