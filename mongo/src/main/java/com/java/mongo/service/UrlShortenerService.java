package com.java.mongo.service;

import com.java.mongo.entity.ShortenedUrl;
import com.java.mongo.repository.ShortenedUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UrlShortenerService {
    @Autowired
    private ShortenedUrlRepository repository;
        public String shortenUrl(String originalUrl) {
        String shortCode = generateShortCode();

        ShortenedUrl shortenedUrl = new ShortenedUrl();
        shortenedUrl.setOriginalUrl(originalUrl);
        shortenedUrl.setShortCode(shortCode);

        // Set the expiration time to 5 minutes from now
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5);
        shortenedUrl.setExpirationTimestamp(expirationTime);

        repository.save(shortenedUrl);

        return shortCode;
    }

    public String getOriginalUrl(String shortCode) {
        ShortenedUrl shortenedUrl = repository.findByShortCode(shortCode);
        if (shortenedUrl != null) {
            return shortenedUrl.getOriginalUrl();
        }
        return null;
    }

    private String generateShortCode() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int length = 6;

        StringBuilder shortCodeBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * characters.length());
            char randomChar = characters.charAt(randomIndex);
            shortCodeBuilder.append(randomChar);
        }

        return shortCodeBuilder.toString();
    }
}