package com.java.mongo.controller;

import com.java.mongo.entity.ShortenedUrl;
import com.java.mongo.repository.ShortenedUrlRepository;
import com.java.mongo.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UrlShortenerController {
    private final UrlShortenerService urlShortenerService;
    private final ShortenedUrlRepository shortenedUrlRepository;

   @Autowired
    public UrlShortenerController(UrlShortenerService urlShortenerService, ShortenedUrlRepository shortenedUrlRepository) {
        this.urlShortenerService = urlShortenerService;
        this.shortenedUrlRepository = shortenedUrlRepository;
    }

     //To post the Url data
    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("url");
        String shortCode = urlShortenerService.shortenUrl(originalUrl);
        String shortenedUrl = "http://localhost:8080/api/" + shortCode;
        return ResponseEntity.ok(shortenedUrl);
    }

    //To get the data from the Url
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortCode) {
        String originalUrl = urlShortenerService.getOriginalUrl(shortCode);

        if (originalUrl != null) {
            ShortenedUrl shortenedUrl = shortenedUrlRepository.findByShortCode(shortCode);
            LocalDateTime expirationTime = shortenedUrl.getExpirationTimestamp();

            if (expirationTime == null || LocalDateTime.now().isBefore(expirationTime)) {
                HttpHeaders headers = new HttpHeaders();      // If the URL is valid and not expired, perform redirection
                headers.setLocation(URI.create(originalUrl));
                return new ResponseEntity<>(headers, HttpStatus.FOUND);
            } else {
                return ResponseEntity.status(HttpStatus.GONE).build();   // If the URL has expired, return a 'Gone' response
            }
        }

        return ResponseEntity.notFound().build();
    }
}
