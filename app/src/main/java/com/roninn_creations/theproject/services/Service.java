package com.roninn_creations.theproject.services;

import com.google.gson.Gson;
import com.roninn_creations.theproject.network.RequestHandler;

public abstract class Service {

    String path;
    Gson gson;
    RequestHandler requestHandler;
}
