package com.moodic.music;

import com.moodic.utils.SimpleCache;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SpotifyMusicAPI implements MusicAPI {

    private static final String ACCESS_TOKEN_ENDPOINT = "https://accounts.spotify.com/api/token";
    private static final String RECOMMENDATIONS_ENDPOINT = "https://api.spotify.com/v1/recommendations";
    private static final String CLIENT_ID = "e64af65958784d6d891c611c43837f66";

    // TODO: Hide this secret.
    private static final String CLIENT_SECRET = "fee4e1e407314b96b52a72cce365faa4";

    private static final SimpleCache simpleCache = new SimpleCache();

    @Override
    public String[] getSongsByGenre(String genre) {
        final String URI_ENDPOINT = String.format("%s?seed_genres=%s", RECOMMENDATIONS_ENDPOINT, genre.toLowerCase());
        String accessToken;
        if (simpleCache.isCacheStale()) {
            String accessTokenResponse = this.getAccessTokenResponse();
            accessToken = this.getAccessToken(accessTokenResponse);
        } else {
            accessToken = simpleCache.getToken();
        }
        String songRecommendationsResponse = this.getRecommendationsByGenreResponse(accessToken, URI_ENDPOINT);
        return this.getRecommendationsByGenre(songRecommendationsResponse);
    }

    private String getAccessTokenResponse() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ACCESS_TOKEN_ENDPOINT))
                .method("POST", HttpRequest.BodyPublishers.ofString(
                        String.format("grant_type=client_credentials&client_id=%s&client_secret=%s", CLIENT_ID, CLIENT_SECRET)))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();
        try {
            return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            return HttpStatus.INTERNAL_SERVER_ERROR.toString();
        }
    }

    private String getAccessToken(String accessTokenResponse) {
        try {
            String accessToken = new JSONObject(accessTokenResponse).get("access_token").toString();
            simpleCache.updateCache(accessToken);
            return simpleCache.getToken();
        } catch (JSONException e) {
            // TODO: Find a way to return the actual http status in a response or something
            return HttpStatus.INTERNAL_SERVER_ERROR.toString();
        }
    }

    private String getRecommendationsByGenreResponse(String accessToken, String endpoint) {
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

    private String[] getRecommendationsByGenre(String songRecommendationsResponse) {
        try {
            JSONArray tracks = new JSONObject(songRecommendationsResponse).getJSONArray("tracks");
            String[] trackList = new String[tracks.length()];
            for (int i = 0; i < tracks.length(); i++) {
                trackList[i] = tracks.getJSONObject(i).get("name").toString();
            }
            return trackList;
        } catch (JSONException e) {
            return new String[]{HttpStatus.INTERNAL_SERVER_ERROR.toString()};
        }
    }
}
