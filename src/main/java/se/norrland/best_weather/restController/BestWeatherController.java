package se.norrland.best_weather.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.norrland.best_weather.Service.BestWeather;
import se.norrland.best_weather.Service.BestWeatherService;

@RestController
public class BestWeatherController {
    private final BestWeatherService bestWeatherService;

    public BestWeatherController(BestWeatherService bestWeatherService) {
        this.bestWeatherService = bestWeatherService;
    }

    @GetMapping("/best-weather")
    public BestWeather getBestWeather() {
        return bestWeatherService.getBestWeather();
    }
}
