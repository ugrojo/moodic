package com.moodic.weather;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import software.amazon.awssdk.services.dynamodb.endpoints.internal.Value;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// TODO: Add cache for one hour?
public class OpenWeatherAPI implements WeatherAPI {

    private static final String ENDPOINT = "http://api.openweathermap.org/data/2.5/weather";
    private static final String APP_ID = "7da5b758b2f9176fa6aedf47f21b1a03";

    private static final double KELVIN_TO_CELSIUS = -273.15;


    @Override
    public String getWeatherByCity(String city) {
        final String URI_ENDPOINT = String.format("%s?appid=%s&q=%s", ENDPOINT, APP_ID, city);
        String responseBody = this.getWeatherResponseBody(URI_ENDPOINT);
        return this.getWeather(responseBody);
    }

    private String getWeatherResponseBody(String endpoint) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .headers("Accept", "application/json")
                .build();
        try {
            return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            return HttpStatus.INTERNAL_SERVER_ERROR.toString();
        }
    }

    private String getWeather(String responseBody) {
        try {
            String kelvinTemp = new JSONObject(responseBody).getJSONObject("main").get("temp").toString();
            double celsiusTemp = Double.parseDouble(kelvinTemp) + KELVIN_TO_CELSIUS;
            return String.format("%.2f", celsiusTemp);
        } catch (JSONException e) {
            return HttpStatus.INTERNAL_SERVER_ERROR.toString();
        }
    }

}
