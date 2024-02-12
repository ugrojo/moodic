package com.moodic.config;

import com.moodic.db.MoodicRequestRepository;
import com.moodic.moodic.MoodicControlPlane;
import com.moodic.music.MusicAPI;
import com.moodic.music.SpotifyMusicAPI;
import com.moodic.weather.OpenWeatherAPI;
import com.moodic.weather.OpenWeatherAPIClient;
import com.moodic.weather.WeatherAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

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

    @Bean
    public DynamoDbClient getDynamoDbClient() {
        AwsCredentialsProvider credentialsProvider = DefaultCredentialsProvider.builder().build();
        return DynamoDbClient.builder()
                .region(Region.US_WEST_1)
                .credentialsProvider(credentialsProvider).build();
    }

    @Bean
    public DynamoDbEnhancedClient getDynamoDbEnhancedClient() {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(getDynamoDbClient())
                .build();
    }

    @Bean
    public MoodicRequestRepository getMoodicRequestRepository() {
        return new MoodicRequestRepository();
    }

    @Bean
    public OpenWeatherAPIClient getOpenWeatherAPIClient() {
        return new OpenWeatherAPIClient();
    }

}
