package com.moodic.moodic;

import com.moodic.music.MusicAPI;
import com.moodic.weather.WeatherAPI;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class MoodicControlPlaneTest {

    @Mock
    WeatherAPI weatherAPI;

    @Mock
    MusicAPI musicAPI;

    @InjectMocks
    MoodicControlPlane moodicControlPlane;

    String[] summerSongs = new String[]{"summer1", "summer2", "summer3"};
    String[] springSongs = new String[]{"spring1", "spring2", "spring3"};
    String[] autumnSongs = new String[]{"autumn1", "autumn2", "autumn3"};
    String[] winterSongs = new String[]{"winter1", "winter2", "winter3"};

    @Before
    public void setUp() {
        Mockito.when(musicAPI.getSongsByGenre(MoodicControlPlane.Mood.SUMMER.genre()))
                .thenReturn(summerSongs);
        Mockito.when(musicAPI.getSongsByGenre(MoodicControlPlane.Mood.SPRING.genre()))
                .thenReturn(springSongs);
        Mockito.when(musicAPI.getSongsByGenre(MoodicControlPlane.Mood.AUTUMN.genre()))
                .thenReturn(autumnSongs);
        Mockito.when(musicAPI.getSongsByGenre(MoodicControlPlane.Mood.WINTER.genre()))
                .thenReturn(winterSongs);
    }

    @Test
    public void getSummerSongs() throws Exception {
        Mockito.when(weatherAPI.getWeatherByCity(anyString())).thenReturn("31");
        String[] resultSongs = moodicControlPlane.getSongsForCityWeather("Zapopan");
        Assert.assertArrayEquals(summerSongs, resultSongs);
    }

    @Test
    public void getSpringSongs() throws Exception {
        Mockito.when(weatherAPI.getWeatherByCity(anyString())).thenReturn("20");
        String[] resultSongs = moodicControlPlane.getSongsForCityWeather("Zapopan");
        Assert.assertArrayEquals(springSongs, resultSongs);
    }

    @Test
    public void getAutumnSongs() throws Exception {
        Mockito.when(weatherAPI.getWeatherByCity(anyString())).thenReturn("12");
        String[] resultSongs = moodicControlPlane.getSongsForCityWeather("Zapopan");
        Assert.assertArrayEquals(autumnSongs, resultSongs);
    }

    @Test
    public void getWinterSongs() throws Exception {
        Mockito.when(weatherAPI.getWeatherByCity(anyString())).thenReturn("9");
        String[] resultSongs = moodicControlPlane.getSongsForCityWeather("Zapopan");
        Assert.assertArrayEquals(winterSongs, resultSongs);
    }
}
