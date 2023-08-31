package com.java.mongo.MongoDBConfig;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

public class MongoDBConfig extends AbstractMongoClientConfiguration {
    @Override
    protected String getDatabaseName() {
        return "urlshortenerdb";
    }
    @Override
    public MongoClient mongoClient(){
        return MongoClients.create("mongodb://localhost:27017");
    }
}
