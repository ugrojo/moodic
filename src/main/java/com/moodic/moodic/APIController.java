package com.moodic.moodic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseStatus(HttpStatus.OK)
public class APIController {

    @Autowired
    MoodicControlPlane controlPlane;

    @GetMapping("/api/v1/songs")
    public ResponseEntity<String[]> getSongs(@RequestParam(value="city", defaultValue = "zapopan") String city) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(controlPlane.getSongsForCityWeather(city));
    }
}
