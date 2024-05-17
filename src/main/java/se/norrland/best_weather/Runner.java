package se.norrland.best_weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.norrland.best_weather.clients.domain.Humidity;
import se.norrland.best_weather.clients.domain.Temperature;
import se.norrland.best_weather.clients.domain.WeatherInfo;
import se.norrland.best_weather.clients.domain.WeatherSrc;
import se.norrland.best_weather.clients.smhi.SmhiClient;
import se.norrland.best_weather.clients.smhi.model.Smhi;

import java.time.LocalDateTime;

@Component
public class Runner implements CommandLineRunner {

    private final SmhiClient client;

    public Runner(SmhiClient client) {
        this.client = client;
    }

    @Override
    public void run(String... args) {
        Smhi smhi = client.getSmhiData();
        Temperature temp = client.extractTemperature(smhi);
        System.out.println(temp);

        Humidity humidity = client.extractHumidity(smhi);

        WeatherInfo weatherInfo = new WeatherInfo(WeatherSrc.SMHI, temp, humidity, LocalDateTime.now());

        System.out.println(weatherInfo);

    }
}
