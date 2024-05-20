package se.norrland.best_weather;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.norrland.best_weather.clients.met.MetClient;
import se.norrland.best_weather.clients.met.model.Met;
import se.norrland.best_weather.clients.smhi.SmhiClient;
import se.norrland.best_weather.clients.smhi.model.Smhi;

import java.time.LocalDateTime;

@Component
public class Runner implements CommandLineRunner {

    private final SmhiClient client;
    private final MetClient metClient;

    public Runner(SmhiClient client, MetClient metClient) {
        this.client = client;
        this.metClient = metClient;
    }

    @Override
    public void run(String... args) {
        Smhi smhi = client.getSmhiData();


    }
}
