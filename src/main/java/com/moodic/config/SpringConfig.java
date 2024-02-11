package com.moodic.config;

import com.moodic.moodic.MoodicControlPlane;
import com.moodic.music.MusicAPI;
import com.moodic.music.SpotifyMusicAPI;
import com.moodic.utils.SimpleCache;
import com.moodic.weather.OpenWeatherAPI;
import com.moodic.weather.WeatherAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public WeatherAPI weatherAPI() {
        return new OpenWeatherAPI();
    }

    @Bean
    public MusicAPI musicAPI() {

        return new SpotifyMusicAPI();
    }

    @Bean
    public MoodicControlPlane moodicControlPlane() {
        return new MoodicControlPlane();
    }
}
