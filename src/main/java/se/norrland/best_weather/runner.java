package se.norrland.best_weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.norrland.best_weather.clients.smhi.SmhiClient;

@Component
public class runner implements CommandLineRunner {

    @Autowired
    SmhiClient client;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(client.extractTemperature());

    }
}
