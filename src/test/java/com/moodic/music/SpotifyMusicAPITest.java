package com.moodic.music;

import com.moodic.utils.SimpleCache;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class SpotifyMusicAPITest {

    @Mock
    SimpleCache simpleCache;

    @Mock
    SpotifyMusicAPIClient spotifyMusicAPIClient;

    @InjectMocks
    SpotifyMusicAPI spotifyMusicAPI;

    @Test
    public void testGetSongsByGenre() {
        String testTracksResponse = "{\"tracks\": [{\"name\": \"Let it be\"}, {\"name\": \"Imagine\"}, {\"name\": \"Get Back\"}]}";
        String[] testTracks = new String[]{"Let it be", "Imagine", "Get Back"};
        Mockito.when(simpleCache.isCacheStale()).thenReturn(false);
        Mockito.when(simpleCache.getToken()).thenReturn("someAccessToken");
        Mockito.when(spotifyMusicAPIClient.getRecommendationsByGenreResponse(anyString(), anyString())).thenReturn(testTracksResponse);
        String[] resultTracks = spotifyMusicAPI.getSongsByGenre("pop");
        Assert.assertArrayEquals(testTracks, resultTracks);
    }
}
