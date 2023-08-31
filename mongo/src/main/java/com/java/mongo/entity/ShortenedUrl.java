package com.java.mongo.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Document(collection = "shortened_urls")
public class ShortenedUrl {
    @Id
    private String id;
    private String originalUrl;
    private String shortCode;

    //Here I am taking time column to make expire url in 5 mins
    private LocalDateTime expirationTimestamp;

    // Default constructor
    public ShortenedUrl() {
    }

    // Constructor with parameters
    public ShortenedUrl(String originalUrl, String shortCode) {
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
    }

    // using Getters and Setters for above fields
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }
    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    // Getters  and Setters for short code
    public String getShortCode() {
        return shortCode;
    }
    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public LocalDateTime getExpirationTimestamp() {
        return expirationTimestamp;
    }
    public void setExpirationTimestamp(LocalDateTime expirationTimestamp) {
        this.expirationTimestamp = expirationTimestamp;
    }
}

