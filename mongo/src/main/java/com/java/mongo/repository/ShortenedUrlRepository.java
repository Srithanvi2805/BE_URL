package com.java.mongo.repository;

import com.java.mongo.entity.ShortenedUrl;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShortenedUrlRepository extends MongoRepository<ShortenedUrl, String> {
    ShortenedUrl findByShortCode(String shortCode);
}



