package se.norrland.best_weather.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.norrland.best_weather.service.BestWeather;
import se.norrland.best_weather.service.BestWeatherService;
import se.norrland.best_weather.util.DateTimeFormatter;

@Controller
public class BestWeatherController {

    private final BestWeatherService bestWeatherService;

    public BestWeatherController(BestWeatherService bestWeatherService) {
        this.bestWeatherService = bestWeatherService;
    }

    @GetMapping("/best-weather")
    public String getBestWeather(Model model) {
        //model.addAttribute("dateTime", ()); ???
        model.addAttribute("bestWeather", bestWeatherService.getBestWeather());
        return "weather";
    }
}
