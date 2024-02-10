package com.moodic.weather;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenWeatherAPI implements WeatherAPI {

    private static final String ENDPOINT = "http://api.openweathermap.org/data/2.5/weather";
    private static final String APP_ID = "7da5b758b2f9176fa6aedf47f21b1a03";

    private static final double KELVIN_TO_CELSIUS = -273.15;

    @Override
    public String getWeatherByCity(String city) {
        final String URI_ENDPOINT = String.format("%s?appid=%s&q=%s", ENDPOINT, APP_ID, city);
        String responseBody = this.getResponseBody(URI_ENDPOINT);
        return this.getCelsiusTemp(responseBody) + "C";
    }

    private String getResponseBody(String endpoint) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            return HttpStatus.INTERNAL_SERVER_ERROR.toString();
        }
    }

    private String getCelsiusTemp(String jsonResponse) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            String kelvinTemp = jsonObject.getJSONObject("main").get("temp").toString();
            double celsiusTemp = Double.parseDouble(kelvinTemp) + KELVIN_TO_CELSIUS;
            return String.valueOf(celsiusTemp);
        } catch (JSONException err){
            return err.toString();
        }
    }

}
