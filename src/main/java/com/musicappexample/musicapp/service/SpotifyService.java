package com.musicappexample.musicapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SpotifyService {

    private final RestTemplate restTemplate;

    @Autowired
    public SpotifyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getTrack(String accessToken, String trackId) {
        if (accessToken == null || accessToken.isEmpty()) {
            throw new IllegalArgumentException("Access token cannot be null or empty");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        ResponseEntity<String> response = restTemplate.exchange("https://api.spotify.com/v1/tracks/" + trackId, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        return response.getBody();
    }
    public String getRecommendations(String accessToken) {
        if (accessToken == null || accessToken.isEmpty()) {
            throw new IllegalArgumentException("Access token cannot be null or empty");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        ResponseEntity<String> response = restTemplate.exchange("https://api.spotify.com/v1/recommendations", HttpMethod.GET, new HttpEntity<>(headers), String.class);
        return response.getBody();
    }

    public String getTracks(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        ResponseEntity<String> response = restTemplate.exchange("https://api.spotify.com/v1/tracks", HttpMethod.GET, new HttpEntity<>(headers), String.class);
        return response.getBody();
    }
}