package com.roninn_creations.theproject;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roninn_creations.theproject.models.Place;
import com.roninn_creations.theproject.models.Review;
import com.roninn_creations.theproject.models.User;
import com.roninn_creations.theproject.services.IService;
import com.roninn_creations.theproject.services.PlacesService;
import com.roninn_creations.theproject.network.RequestHandler;
import com.roninn_creations.theproject.services.ReviewsService;
import com.roninn_creations.theproject.network.Volley;
import com.roninn_creations.theproject.services.UsersService;

public class TheProjectApplication extends Application {

    private static Gson gson;
    private static RequestHandler requestHandler;
    private static IService<Place> placesService;
    private static IService<Review> reviewsService;
    private static IService<User> usersService;

    @Override
    public void onCreate(){
        super.onCreate();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(getString(R.string.date_format)).create();
        gson = gsonBuilder.create();

        Volley volley = new Volley(this);
        requestHandler = new RequestHandler(getString(R.string.base_url), getString(R.string.test_token), volley.getRequestQueue());
        placesService = new PlacesService(getString(R.string.places_path), gson, requestHandler);
        reviewsService = new ReviewsService(getString(R.string.reviews_path), gson, requestHandler);
        usersService = new UsersService(getString(R.string.users_path), gson, requestHandler);
    }

    public static Gson getGson(){
        return gson;
    }

    public static RequestHandler getRequestHandler(){
        return requestHandler;
    }

    public static IService<Place> getPlacesService(){
        return placesService;
    }

    public static IService<Review> getReviewsService(){
        return reviewsService;
    }

    public static IService<User> getUsersService(){
        return usersService;
    }
}
