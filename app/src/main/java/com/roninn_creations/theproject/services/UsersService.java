package com.roninn_creations.theproject.services;

import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.roninn_creations.theproject.models.User;
import com.roninn_creations.theproject.network.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class UsersService extends Service implements IService<User> {

    public UsersService(String path, Gson gson, RequestHandler requestHandler){
        super(path, gson, requestHandler);
    }

    public void create(User user, Consumer<User> onResponse,
                       Consumer<String> onError, String tag){
        Consumer<JSONObject> responseConsumer = (JSONObject response) -> {
            if (onResponse != null){
                User returnedUser = gson.fromJson(response.toString(), User.class);
                onResponse.accept(returnedUser);
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
            JSONObject jsonUser = new JSONObject(gson.toJson(user));
            requestHandler.post(path, jsonUser, responseConsumer, errorConsumer, tag);
        } catch (JSONException exception) {
            Log.e(tag, "ERROR: JSON exception!", exception);
            onError.accept("Connection error");
        }
    }

    public void readMany(String params, Consumer<List<User>> onResponse,
                         Consumer<String> onError, String tag){
        Consumer<JSONArray> responseConsumer = (JSONArray response) -> {
            if (onResponse != null){
                try {
                    List<User> users = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++)
                        users.add(gson.fromJson(response.getString(i), User.class));
                    onResponse.accept(users);
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

    public void read(String id, Consumer<User> onResponse,
                     Consumer<String> onError, String tag){
        Consumer<JSONObject> responseConsumer = (JSONObject response) -> {
                if (onResponse != null){
                    User user = gson.fromJson(response.toString(), User.class);
                    onResponse.accept(user);
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

    public void update(User user, Consumer<User> onResponse,
                       Consumer<String> onError, String tag){
        Consumer<JSONObject> responseConsumer = (JSONObject response) -> {
            if (onResponse != null){
                User returnedUser = gson.fromJson(response.toString(), User.class);
                onResponse.accept(returnedUser);
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
            JSONObject jsonUser = new JSONObject(gson.toJson(user));
            requestHandler.put(path + user.getId(), jsonUser, responseConsumer, errorConsumer, tag);
        } catch (JSONException exception) {
            Log.e(tag, "ERROR: JSON exception!", exception);
            onError.accept("Connection error");
        }
    }

    public void delete(String id, Consumer<User> onResponse,
                       Consumer<String> onError, String tag){
        Consumer<JSONObject> responseConsumer = (JSONObject response) -> {
            if (onResponse != null){
                User user = gson.fromJson(response.toString(), User.class);
                onResponse.accept(user);
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
        requestHandler.delete(path + id, responseConsumer, errorConsumer, tag);
    }
}
