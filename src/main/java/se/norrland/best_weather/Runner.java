package se.norrland.best_weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.norrland.best_weather.clients.domain.Temperature;
import se.norrland.best_weather.clients.smhi.SmhiClient;
import se.norrland.best_weather.clients.smhi.model.Smhi;

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

    }
}
