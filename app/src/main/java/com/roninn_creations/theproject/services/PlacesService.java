package com.roninn_creations.theproject.services;

import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.roninn_creations.theproject.models.Place;
import com.roninn_creations.theproject.network.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PlacesService extends Service implements IService<Place> {

    public PlacesService(String path, Gson gson, RequestHandler requestHandler){
        super(path, gson, requestHandler);
    }

    public void create(Place place, Consumer<Place> onResponse,
                       Consumer<String> onError, String tag){
        Consumer<JSONObject> responseConsumer = (JSONObject response) -> {
            if (onResponse != null){
                Place returnedPlace = gson.fromJson(response.toString(), Place.class);
                onResponse.accept(returnedPlace);
            }
        };
        Consumer<VolleyError> errorConsumer = (VolleyError error) -> {
            if (onError != null){
                try {
                    JSONObject responseBody = new JSONObject(
                            new String(error.networkResponse.data, StandardCharsets.UTF_8));
                    onError.accept(responseBody.getString("message"));
                } catch (Exception exception) {
                    onError.accept("Connection error");
                }
            }
        };
        try {
            JSONObject jsonPlace = new JSONObject(gson.toJson(place));
            requestHandler.post(path, jsonPlace, responseConsumer, errorConsumer, tag);
        }
        catch (JSONException exception) {
            Log.e(tag, "ERROR: JSON exception!", exception);
            onError.accept("Connection error");
        }
    }

    public void readMany(String params, Consumer<List<Place>> onResponse,
                         Consumer<String> onError, String tag){
        Consumer<JSONArray> responseConsumer = (JSONArray response) -> {
            if (onResponse != null){
                try {
                    List<Place> places = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++)
                        places.add(gson.fromJson(response.getString(i), Place.class));
                    onResponse.accept(places);
                } catch (JSONException exception) {
                    Log.e(tag, "ERROR: JSON exception!", exception);
                    onError.accept("Connection error");
                }
            }
        };
        Consumer<VolleyError> errorConsumer = (VolleyError error) -> {
            if (onError != null){
                try {
                    JSONObject responseBody = new JSONObject(
                            new String(error.networkResponse.data, StandardCharsets.UTF_8));
                    onError.accept(responseBody.getString("message"));
                } catch (Exception exception) {
                    onError.accept("Connection error");
                }
            }
        };
        requestHandler.getArray(path + params, responseConsumer, errorConsumer, tag);
    }

    public void read(String id, Consumer<Place> onResponse,
                     Consumer<String> onError, String tag){
        Consumer<JSONObject> responseConsumer = (JSONObject response) -> {
            if (onResponse != null){
                Place place = gson.fromJson(response.toString(), Place.class);
                onResponse.accept(place);
            }
        };
        Consumer<VolleyError> errorConsumer = (VolleyError error) -> {
            if (onError != null){
                try {
                    JSONObject responseBody = new JSONObject(
                            new String(error.networkResponse.data, StandardCharsets.UTF_8));
                    onError.accept(responseBody.getString("message"));
                } catch (Exception exception) {
                    onError.accept("Connection error");
                }
            }
        };
        requestHandler.get(path + id, responseConsumer, errorConsumer, tag);
    }

    public void update(Place place, Consumer<Place> onResponse,
                       Consumer<String> onError, String tag){
        Consumer<JSONObject> responseConsumer = (JSONObject response) -> {
                if (onResponse != null){
                    Place returnedPlace = gson.fromJson(response.toString(), Place.class);
                    onResponse.accept(returnedPlace);
                }
        };
        Consumer<VolleyError> errorConsumer = (VolleyError error) -> {
            if (onError != null){
                try {
                    JSONObject responseBody = new JSONObject(
                            new String(error.networkResponse.data, StandardCharsets.UTF_8));
                    onError.accept(responseBody.getString("message"));
                } catch (Exception exception){
                    onError.accept("Connection error");
                }
            }
        };
        try {
            JSONObject jsonPlace = new JSONObject(gson.toJson(place));
            requestHandler.put(path + place.getId(), jsonPlace, responseConsumer, errorConsumer, tag);
        }
        catch (JSONException exception) {
            Log.e(tag, "ERROR: JSON exception!", exception);
            onError.accept("Connection error");
        }
    }

    public void delete(String id, Consumer<Place> onResponse,
                       Consumer<String> onError, String tag){
        Consumer<JSONObject> responseConsumer = (JSONObject response) -> {
            if (onResponse != null){
                Place place = gson.fromJson(response.toString(), Place.class);
                onResponse.accept(place);
            }
        };
        Consumer<VolleyError> errorConsumer = (VolleyError error) -> {
            if (onError != null){
                try {
                    JSONObject responseBody = new JSONObject(
                            new String(error.networkResponse.data, StandardCharsets.UTF_8));
                    onError.accept(responseBody.getString("message"));
                } catch (Exception exception){
                    onError.accept("Connection error");
                }
            }
        };
        requestHandler.delete(path + id, responseConsumer, errorConsumer, tag);
    }
}