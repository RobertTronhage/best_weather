package se.norrland.best_weather.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.norrland.best_weather.Service.BestWeatherService;

@Controller
public class BestWeatherController {
    private final BestWeatherService bestWeatherService;

    public BestWeatherController(BestWeatherService bestWeatherService) {
        this.bestWeatherService = bestWeatherService;
    }


}
