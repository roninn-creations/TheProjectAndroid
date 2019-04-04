package com.roninn_creations.theproject.network;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonConverter {
    private static JsonConverter instance;
    private Gson gson;

    private JsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
        gson = gsonBuilder.create();
    }

    public static synchronized JsonConverter getInstance() {
        if (instance == null) {
            instance = new JsonConverter();
        }
        return instance;
    }

    public Gson getGson() {
        return gson;
    }
}
