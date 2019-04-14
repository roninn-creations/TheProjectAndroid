package com.roninn_creations.theproject.services;

import android.util.Log;

import com.google.gson.Gson;
import com.roninn_creations.theproject.models.Place;
import com.roninn_creations.theproject.network.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.roninn_creations.theproject.TheProjectApplication.getGson;

public class PlacesService extends Service implements IService<Place> {

    public PlacesService(String path, Gson gson, RequestHandler requestHandler){
        this.path = path;
        this.gson = gson;
        this.requestHandler = requestHandler;
    }

    public void readAll(Consumer<List<Place>> onResponse, String tag){
        Consumer<JSONObject> consumer = new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject response) {
                List<Place> places = new ArrayList<>();
                JSONArray jsonPlaces = response.optJSONArray("rows");
                try {
                    for (int i = 0; i < jsonPlaces.length(); i++)
                        places.add(getGson().fromJson(jsonPlaces.getString(i), Place.class));
                    onResponse.accept(places);
                }
                catch (JSONException e) {
                    String message = "ERROR:" + e.getMessage();
                    Log.w(tag, message);
                }
            }
        };
        requestHandler.get(path, consumer, tag);
    }

    public void read(String id, Consumer<Place> onResponse, String tag){
        Consumer<JSONObject> consumer = new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject response) {
                Place place = gson.fromJson(response.toString(), Place.class);
                onResponse.accept(place);
            }
        };
        requestHandler.get(path + id, consumer, tag);
    }

    public void create(Place place, Consumer<Place> onResponse, String tag){
        Consumer<JSONObject> consumer = new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject response) {
                Place place = gson.fromJson(response.toString(), Place.class);
                onResponse.accept(place);
            }
        };
        try {
            JSONObject jsonPlace = new JSONObject(gson.toJson(place));
            requestHandler.post(path, jsonPlace, consumer, tag);
        }
        catch (JSONException e) {
            String message = "ERROR:" + e.getMessage();
            Log.w(tag, message);
        }
    }

    public void update(Place place, Consumer<Place> onResponse, String tag){
        Consumer<JSONObject> consumer = new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject response) {
                Place place = gson.fromJson(response.toString(), Place.class);
                onResponse.accept(place);
            }
        };
        try {
            JSONObject jsonPlace = new JSONObject(gson.toJson(place));
            requestHandler.put(path + place.getId(), jsonPlace, consumer, tag);
        }
        catch (JSONException e) {
            String message = "ERROR:" + e.getMessage();
            Log.w(tag, message);
        }
    }

    public void delete(String id, Consumer<Place> onResponse, String tag){
        Consumer<JSONObject> consumer = new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject response) {
                Place place = gson.fromJson(response.toString(), Place.class);
                onResponse.accept(place);
            }
        };
        requestHandler.delete(path + id, consumer, tag);
    }
}