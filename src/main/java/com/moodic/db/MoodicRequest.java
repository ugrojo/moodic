package com.moodic.db;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.Instant;

@DynamoDbBean
public class MoodicRequest {

    private String requestId;
    private Instant requestInstant;
    private String city;
    private double temperatureC;
    private String songsReturned;
    private String musicGenre;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("RequestId")
    public String getRequestId() {
        return requestId;
    }
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @DynamoDbAttribute("RequestInstant")
    public Instant getRequestInstant() {
        return requestInstant;
    }

    public void setRequestInstant(Instant requestInstant) {
        this.requestInstant = requestInstant;
    }

    @DynamoDbAttribute("City")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @DynamoDbAttribute("TemperatureC")
    public double getTemperatureC() {
        return temperatureC;
    }

    public void setTemperatureC(double temperatureC) {
        this.temperatureC = temperatureC;
    }

    @DynamoDbAttribute("SongsReturned")
    public String getSongsReturned() {
        return songsReturned;
    }

    public void setSongsReturned(String songsReturned) {
        this.songsReturned = songsReturned;
    }

    @DynamoDbAttribute("MusicGenre")
    public String getMusicGenre() {
        return musicGenre;
    }

    public void setMusicGenre(String musicGenre) {
        this.musicGenre = musicGenre;
    }
}
