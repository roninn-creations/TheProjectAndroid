package com.roninn_creations.theproject.services;

import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.roninn_creations.theproject.models.RegisterModel;
import com.roninn_creations.theproject.models.User;
import com.roninn_creations.theproject.network.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class AuthService extends Service {

    public AuthService(String path, Gson gson, RequestHandler requestHandler){
        super(path, gson, requestHandler);
    }

    public void login(String email, String password, BiConsumer<User, String> onResponse,
                      Consumer<String> onError, String tag) {
        Consumer<JSONObject> responseConsumer = (JSONObject response) -> {
            if (onResponse != null){
                try {
                    User user = gson.fromJson(response.getString("user"), User.class);
                    String token = response.getString("token");
                    onResponse.accept(user, token);
                } catch (JSONException exception){
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
        requestHandler.getBasic(path + "basic", responseConsumer,
                errorConsumer, email, password, tag);
    }

    public void register(RegisterModel model, BiConsumer<User, String> onResponse,
                         Consumer<String> onError, String tag) {
        Consumer<JSONObject> responseConsumer = (JSONObject response) -> {
            if (onResponse != null){
                try {
                    User returnedUser = gson.fromJson(response.getString("user"), User.class);
                    String token = response.getString("token");
                    onResponse.accept(returnedUser, token);
                } catch (JSONException exception){
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
        try {
            JSONObject jsonModel = new JSONObject(gson.toJson(model));
            requestHandler.post(path, jsonModel, responseConsumer, errorConsumer, tag);
        }
        catch (JSONException exception) {
            Log.e(tag, "ERROR: JSON exception!", exception);
            onError.accept("Connection error");
        }
    }

    public void google(String googleCode, BiConsumer<User, String> onResponse,
                      Consumer<String> onError, String tag) {
        Consumer<JSONObject> responseConsumer = (JSONObject response) -> {
            if (onResponse != null){
                try {
                    User user = gson.fromJson(response.getString("user"), User.class);
                    String token = response.getString("token");
                    onResponse.accept(user, token);
                } catch (JSONException exception){
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
        try {
            GoogleAuthData authData = new GoogleAuthData(googleCode);
            JSONObject jsonData = new JSONObject(gson.toJson(authData));
            requestHandler.post(path + "google", jsonData, responseConsumer,
                    errorConsumer, tag);
        } catch (JSONException exception) {
            Log.e(tag, "ERROR: JSON exception!", exception);
            onError.accept("Connection error");
        }
    }

    public void facebook(String facebookToken, BiConsumer<User, String> onResponse,
                       Consumer<String> onError, String tag) {
        Consumer<JSONObject> responseConsumer = (JSONObject response) -> {
            if (onResponse != null){
                try {
                    User user = gson.fromJson(response.getString("user"), User.class);
                    String token = response.getString("token");
                    onResponse.accept(user, token);
                } catch (JSONException exception){
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
        try {
            FacebookAuthData authData = new FacebookAuthData(facebookToken);
            JSONObject jsonData = new JSONObject(gson.toJson(authData));
            requestHandler.post(path + "facebook", jsonData, responseConsumer,
                    errorConsumer, tag);
        } catch (JSONException exception) {
            Log.e(tag, "ERROR: JSON exception!", exception);
            onError.accept("Connection error");
        }
    }

    private class GoogleAuthData {
        private String code;

        private GoogleAuthData(String code){
            this.code = code;
        }
    }

    private class FacebookAuthData {
        private String token;

        private FacebookAuthData(String token){
            this.token = token;
        }
    }
}
