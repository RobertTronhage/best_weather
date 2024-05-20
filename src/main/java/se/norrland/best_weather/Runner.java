package se.norrland.best_weather;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.norrland.best_weather.clients.met.MetClient;
import se.norrland.best_weather.clients.met.MetData;
import se.norrland.best_weather.clients.met.model.Met;
import se.norrland.best_weather.clients.meteo.MeteoClient;
import se.norrland.best_weather.clients.meteo.MeteoData;
import se.norrland.best_weather.clients.smhi.SmhiClient;
import se.norrland.best_weather.clients.smhi.SmhiData;
import se.norrland.best_weather.clients.smhi.model.Smhi;

import java.time.LocalDateTime;

@Component
public class Runner implements CommandLineRunner {

    private final SmhiClient client;
    private final MetClient metClient;
    private final MeteoClient meteoClient;

    public Runner(SmhiClient client, MetClient metClient, MeteoClient meteoClient) {
        this.client = client;
        this.metClient = metClient;
        this.meteoClient = meteoClient;
    }

    @Override
    public void run(String... args) {

        SmhiData data = client.getSmhiData();
        System.out.println(data);

        MetData mdata = metClient.getMetData();
        System.out.println(mdata);

        MeteoData meData = meteoClient.getMeteoData();
        System.out.println(meData);

    }
}
