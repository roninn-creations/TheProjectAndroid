package com.roninn_creations.theproject.services;

import com.google.gson.Gson;
import com.roninn_creations.theproject.network.RequestHandler;

public class AuthService extends Service {

    public AuthService(String path, Gson gson, RequestHandler requestHandler){
        super(path, gson, requestHandler);
    }
}
