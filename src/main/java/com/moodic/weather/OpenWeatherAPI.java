package com.moodic.weather;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

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
        return this.getWeather(URI_ENDPOINT);
    }

    // TODO: decouple a bit more?
    private String getWeather(String endpoint) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .headers("Accept", "application/json")
                .build();
        try {
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            String kelvinTemp = new JSONObject(response.body()).getJSONObject("main").get("temp").toString();
            double celsiusTemp = Double.parseDouble(kelvinTemp) + KELVIN_TO_CELSIUS;
            return String.format("%.2f", celsiusTemp);
        } catch (IOException | InterruptedException | JSONException e) {
            return HttpStatus.INTERNAL_SERVER_ERROR.toString();
        }
    }

}
