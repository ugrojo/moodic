package com.moodic.moodic;

import com.moodic.music.MusicAPI;
import com.moodic.weather.WeatherAPI;
import org.springframework.beans.factory.annotation.Autowired;

public class MoodicControlPlane {

    enum Mood {
        SUMMER("dance"),
        SPRING("pop"),
        AUTUMN("rock"),
        WINTER("classical");

        private final String genre;
        Mood(String genre) {
            this.genre = genre;
        }

        String genre() {
            return this.genre;
        }
    }

    @Autowired
    WeatherAPI weatherAPI;

    @Autowired
    MusicAPI musicAPI;

    public String[] getSongsForCityWeather(String city) {
        double weather = Double.parseDouble(weatherAPI.getWeatherByCity(city));
        System.out.println(weather);
        String moodGenre;
        if (weather > 30.0) {
            moodGenre = Mood.SUMMER.genre();
        }
        else if (weather >= 15.0) {
            moodGenre = Mood.SPRING.genre();
        }
        else if (weather >= 10.0 && weather <= 14.0) {
            moodGenre = Mood.AUTUMN.genre();
        }
        else {
            moodGenre = Mood.WINTER.genre();
        }
        System.out.println(moodGenre);
        return musicAPI.getSongsByGenre(moodGenre);
    }
}
