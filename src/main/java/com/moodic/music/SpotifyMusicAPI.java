package com.moodic.music;

import com.moodic.utils.SimpleCache;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class SpotifyMusicAPI implements MusicAPI {

    private static final String RECOMMENDATIONS_ENDPOINT = "https://api.spotify.com/v1/recommendations";
    private static final String CLIENT_ID = "e64af65958784d6d891c611c43837f66";

    // TODO: Hide this secret.
    private static final String CLIENT_SECRET = "fee4e1e407314b96b52a72cce365faa4";

    @Autowired
    SimpleCache simpleCache;

    @Autowired
    SpotifyMusicAPIClient spotifyMusicAPIClient;

    @Override
    public String[] getSongsByGenre(String genre) {
        final String URI_ENDPOINT = String.format("%s?seed_genres=%s", RECOMMENDATIONS_ENDPOINT, genre.toLowerCase());
        String accessToken;
        if (simpleCache.isCacheStale()) {
            String accessTokenResponse = spotifyMusicAPIClient.getAccessTokenResponse(CLIENT_ID, CLIENT_SECRET);
            accessToken = this.getAccessToken(accessTokenResponse);
        } else {
            accessToken = simpleCache.getToken();
        }
        String songRecommendationsResponse = spotifyMusicAPIClient.getRecommendationsByGenreResponse(accessToken, URI_ENDPOINT);
        return this.getRecommendationsByGenre(songRecommendationsResponse);
    }

    private String getAccessToken(String accessTokenResponse) {
        try {
            String accessToken = new JSONObject(accessTokenResponse).get("access_token").toString();
            simpleCache.updateCache(accessToken);
            return simpleCache.getToken();
        } catch (JSONException e) {
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
