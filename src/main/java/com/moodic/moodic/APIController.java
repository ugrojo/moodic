package com.moodic.moodic;

import com.moodic.music.MusicAPI;
import com.moodic.weather.WeatherAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIController {

    @Autowired
    WeatherAPI weatherAPI;

    @Autowired
    MusicAPI musicAPI;

    @GetMapping("/")
    public String index() {
        return "Moodic Service API!\n";
    }

    // TODO: Remove this temporary testing endpoint
    @GetMapping("/api/v1/weather")
    public String getWeather(@RequestParam(value="city", defaultValue = "Zapopan") String city) {
        return weatherAPI.getWeatherByCity(city);
    }

    // TODO: This endpoint will change to receive city instead of genre. This is for temporary testing.
    @GetMapping("/api/v1/songs")
    public String[] getSongs(@RequestParam(value="genre", defaultValue = "Rock") String genre) {
        return musicAPI.getSongsByGenre(genre);
    }
}
