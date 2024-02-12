package com.moodic.moodic;

import static org.mockito.ArgumentMatchers.anyString;

import org.junit.Assert;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class APIControllerTests {

    @Mock
    private MoodicControlPlane controlPlane;

    @InjectMocks
    private APIController apiController;

    @Test
    public void getSongs() throws Exception {
        String[] testSongs = new String[]{"Let it be", "Imagine", "Get Back"};
        Mockito.when(controlPlane.getSongsForCityWeather(anyString())).thenReturn(testSongs);
        String[] resultSongs = apiController.getSongs("Zapopan").getBody();
        Assert.assertArrayEquals(testSongs, resultSongs);
    }
}
