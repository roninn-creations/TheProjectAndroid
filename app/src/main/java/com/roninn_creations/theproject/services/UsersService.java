package com.roninn_creations.theproject.services;

import android.util.Log;

import com.google.gson.Gson;
import com.roninn_creations.theproject.models.User;
import com.roninn_creations.theproject.network.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class UsersService extends Service implements IService<User> {

    public UsersService(String path, Gson gson, RequestHandler requestHandler){
        super(path, gson, requestHandler);
    }

    public void readAll(Consumer<List<User>> onResponse, String tag){
        Consumer<JSONObject> consumer = new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject response) {
                try {
                    List<User> users = new ArrayList<>();
                    JSONArray jsonUsers = response.optJSONArray("rows");
                    for (int i = 0; i < jsonUsers.length(); i++)
                        users.add(gson.fromJson(jsonUsers.getString(i), User.class));
                    if (onResponse != null)
                        onResponse.accept(users);
                } catch (JSONException e) {
                    String message = "ERROR:" + e.getMessage();
                    Log.w(tag, message);
                }
            }
        };
        requestHandler.get(path, consumer, tag);
    }

    public void read(String id, Consumer<User> onResponse, String tag){
        Consumer<JSONObject> consumer = new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject response) {
                User user = gson.fromJson(response.toString(), User.class);
                if (onResponse != null)
                    onResponse.accept(user);
            }
        };
        requestHandler.get(path + id, consumer, tag);
    }

    public void create(User user, Consumer<User> onResponse, String tag){
        Consumer<JSONObject> consumer = new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject response) {
                User user = gson.fromJson(response.toString(), User.class);
                if (onResponse != null)
                    onResponse.accept(user);
            }
        };
        try {
            JSONObject jsonUser = new JSONObject(gson.toJson(user));
            requestHandler.post(path, jsonUser, consumer, tag);
        } catch (JSONException e) {
            String message = "ERROR:" + e.getMessage();
            Log.w(tag, message);
        }
    }

    public void update(User user, Consumer<User> onResponse, String tag){
        Consumer<JSONObject> consumer = new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject response) {
                User user = gson.fromJson(response.toString(), User.class);
                if (onResponse != null)
                    onResponse.accept(user);
            }
        };
        try {
            JSONObject jsonUser = new JSONObject(gson.toJson(user));
            requestHandler.put(path + user.getId(), jsonUser, consumer, tag);
        } catch (JSONException e) {
            String message = "ERROR:" + e.getMessage();
            Log.w(tag, message);
        }
    }

    public void delete(String id, Consumer<User> onResponse, String tag){
        Consumer<JSONObject> consumer = new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject response) {
                User user = gson.fromJson(response.toString(), User.class);
                if (onResponse != null)
                    onResponse.accept(user);
            }
        };
        requestHandler.delete(path + id, consumer, tag);
    }
}
