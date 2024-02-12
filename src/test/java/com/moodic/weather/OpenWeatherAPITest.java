package com.moodic.weather;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class OpenWeatherAPITest {

    @Mock
    OpenWeatherAPIClient openWeatherAPIClient;

    @InjectMocks
    OpenWeatherAPI openWeatherAPI;

    @Test
    public void testGetWeatherByCity() {
        String testResponse = "{\"main\": {\"temp\": \"300\"}}";
        String expectedCelsius = "26.85";
        Mockito.when(openWeatherAPIClient.getWeatherResponseBody(anyString())).thenReturn(testResponse);
        String tempCelsius = openWeatherAPI.getWeatherByCity("Zapopan");
        Assert.assertEquals(expectedCelsius, tempCelsius);
    }
}
