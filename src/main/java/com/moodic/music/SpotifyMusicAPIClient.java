package com.moodic.music;

import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SpotifyMusicAPIClient {

    private static final String ACCESS_TOKEN_ENDPOINT = "https://accounts.spotify.com/api/token";

    public String getAccessTokenResponse(String clientId, String clientSecret) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ACCESS_TOKEN_ENDPOINT))
                .method("POST", HttpRequest.BodyPublishers.ofString(
                        String.format("grant_type=client_credentials&client_id=%s&client_secret=%s", clientId, clientSecret)))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();
        try {
            return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            return HttpStatus.INTERNAL_SERVER_ERROR.toString();
        }
    }

    public String getRecommendationsByGenreResponse(String accessToken, String endpoint) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .header("Authorization", " Bearer  " + accessToken)
                .headers("Accept", "application/json")
                .build();
        try {
            return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            return HttpStatus.INTERNAL_SERVER_ERROR.toString();
        }
    }

}
