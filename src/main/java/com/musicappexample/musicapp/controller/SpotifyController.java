package com.musicappexample.musicapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import com.musicappexample.musicapp.service.SpotifyService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/spotify")
public class SpotifyController {

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    @Value("${spotify.redirect.uri}")
    private String redirectUri;

    String accessToken = "";
    @GetMapping("/authorize")
    public RedirectView authorize() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://accounts.spotify.com/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri);
        return new RedirectView(builder.toUriString());
    }

    @GetMapping("/login")
    public ResponseEntity<Void> login() throws URISyntaxException {
        String authorizeUrl = "https://accounts.spotify.com/authorize?client_id=" + clientId + "&response_type=code&redirect_uri=" + redirectUri;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(new URI(authorizeUrl));
        return ResponseEntity.status(302).headers(httpHeaders).build();
    }

    
    private final SpotifyService spotifyService;

    @Autowired
    public SpotifyController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @GetMapping("/tracks/{id}")
    public String getTrack(@PathVariable String id) {
        // get the access token for the current user
        accessToken = getAccessToken(accessToken);
        return spotifyService.getTrack(accessToken, id);
    }

    @GetMapping("/callback")
    public ResponseEntity<String> callback(@RequestParam("code") String code) {
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.setBasicAuth(clientId, clientSecret);

    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("grant_type", "authorization_code");
    map.add("code", code);
    map.add("redirect_uri", redirectUri);

    RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<>(map, headers, HttpMethod.POST, URI.create("https://accounts.spotify.com/api/token"));

    ResponseEntity<String> response = restTemplate.exchange(request, String.class);

    // Add the HTML for the redirect here
    String html = "<html><head><script>setTimeout(function(){ window.location.href = 'http://localhost:3001/tracks'; }, 5000);</script></head><body>" + response.getBody() + "</body></html>";
    return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(html);
    }
    @GetMapping("/recommendations")
    public String getRecommendations() {
        // get the access token for the current user
        accessToken = getAccessToken(accessToken);
        return spotifyService.getRecommendations(accessToken);
    }
    
    private String getAccessToken(@RequestHeader("Authorization") String authHeader) {
        String[] parts = authHeader.split(" ");
        if (parts.length > 1) {
            String jwt = parts[1];

            try {
            Claims claims = Jwts.parser()
                .setSigningKey("b0d73a9c4f484c34895a53023c1ebb8a") // replace with your secret key
                .parseClaimsJws(jwt)
                .getBody();

            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("The token is expired", e);
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("The token is unsupported", e);
        } catch (MalformedJwtException e) {
            throw new RuntimeException("The token is malformed", e);
        } catch (SignatureException e) {
            throw new RuntimeException("The token signature is invalid", e);
        } catch (IllegalArgumentException e) {
                    throw new RuntimeException("The token is empty", e);
        } finally {
                    // Add the finally block to complete the try statement
                }
            }
        else{
            throw new IllegalArgumentException("Invalid authorization header");
        }
        }
    

    @GetMapping("/tracks")
    public String getTracks() {
        // get the access token for the current user
         // Initialize the accessToken variable
        accessToken = getAccessToken(accessToken);
        return spotifyService.getTracks(accessToken);
    }

   
}
