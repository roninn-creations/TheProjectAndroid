package com.roninn_creations.theproject.services;

import com.google.gson.Gson;
import com.roninn_creations.theproject.network.RequestHandler;

abstract class Service {

    final String path;
    final Gson gson;
    final RequestHandler requestHandler;

    Service(String path, Gson gson, RequestHandler requestHandler) {
        this.path = path;
        this.gson = gson;
        this.requestHandler = requestHandler;
    }
}
