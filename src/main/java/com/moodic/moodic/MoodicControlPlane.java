package com.moodic.moodic;

import com.moodic.db.MoodicRequest;
import com.moodic.db.MoodicRequestRepository;
import com.moodic.music.MusicAPI;
import com.moodic.weather.WeatherAPI;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.util.Arrays;
import java.util.UUID;

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

    @Autowired
    MoodicRequestRepository moodicRequestRepository;

    public String[] getSongsForCityWeather(String city) {
        double weather = Double.parseDouble(weatherAPI.getWeatherByCity(city));
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
        String[] recommendedSongs = musicAPI.getSongsByGenre(moodGenre);
        UUID requestId = UUID.randomUUID();
        MoodicRequest request = new MoodicRequest();
        request.setRequestId(requestId.toString());
        request.setRequestInstant(Instant.now());
        request.setCity(city);
        request.setMusicGenre(moodGenre);
        request.setTemperatureC(weather);
        request.setSongsReturned(Arrays.toString(recommendedSongs));

        try {
            moodicRequestRepository.save(request);
        } catch(DynamoDbException e) {
            System.out.println(e);
            System.out.println("Failed to save request to DynamoDB.");
        }
        return recommendedSongs;
    }

}
