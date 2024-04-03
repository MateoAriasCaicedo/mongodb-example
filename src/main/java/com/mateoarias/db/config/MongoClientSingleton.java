package com.mateoarias.db.config;

import com.mateoarias.db.DBCredentials;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class MongoClientSingleton {

  private MongoClientSingleton() {}

  public static MongoClient getMongoClient() {
    return MongoClientHolder.mongoClient;
  }

  private static class MongoClientHolder {
    private static final MongoClient mongoClient = MongoClients.create(DBCredentials.URL);
  }
}
