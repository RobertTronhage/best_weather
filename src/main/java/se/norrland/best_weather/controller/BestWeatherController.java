package se.norrland.best_weather.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.norrland.best_weather.service.BestWeatherService;
import se.norrland.best_weather.service.BestWeather;

@Controller
public class BestWeatherController {
    private final BestWeatherService bestWeatherService;

    public BestWeatherController(BestWeatherService bestWeatherService) {
        this.bestWeatherService = bestWeatherService;
    }

    @GetMapping("/best-weather")
    public BestWeather getBestWeather(Model model) {


//        String dateFormatted = dateTimeFormatter.getFormattedDateTime();
//        model.addAttribute("dateTime", dateFormatted);
//        model.addAttribute("bestWeather", bestWeatherService.getBestWeather();

        return null;
    }
}
