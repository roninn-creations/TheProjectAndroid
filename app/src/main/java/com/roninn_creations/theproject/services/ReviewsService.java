package com.roninn_creations.theproject.services;

import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.roninn_creations.theproject.models.Review;
import com.roninn_creations.theproject.models.User;
import com.roninn_creations.theproject.network.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ReviewsService extends Service implements IService<Review> {

    public ReviewsService(String path, Gson gson, RequestHandler requestHandler){
        super(path, gson, requestHandler);
    }

    public void create(Review review, Consumer<Review> onResponse,
                       Consumer<String> onError, String tag){
        Consumer<JSONObject> responseConsumer = (JSONObject response) -> {
            if (onResponse != null){
                try {
                    Review returnedReview = gson.fromJson(response.toString(), Review.class);
                    JSONObject jsonUser = response.getJSONObject("user");
                    User user = gson.fromJson(jsonUser.toString(), User.class);
                    returnedReview.setUser(user);
                    onResponse.accept(returnedReview);
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
        try {
            ReviewCreateModel createModel = new ReviewCreateModel(review);
            JSONObject jsonModel = new JSONObject(gson.toJson(createModel));
            requestHandler.post(path, jsonModel, responseConsumer, errorConsumer, tag);
        } catch (JSONException exception) {
            Log.e(tag, "ERROR: JSON exception!", exception);
            onError.accept("Connection error");
        }
    }

    public void readMany(String params, Consumer<List<Review>> onResponse,
                         Consumer<String> onError, String tag){
        Consumer<JSONArray> responseConsumer = (JSONArray response) -> {
            if (onResponse != null){
                try {
                    List<Review> reviews = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++){
                        JSONObject jsonReview = response.getJSONObject(i);
                        Review review = gson.fromJson(jsonReview.toString(), Review.class);
                        JSONObject jsonUser = jsonReview.getJSONObject("user");
                        User user = gson.fromJson(jsonUser.toString(), User.class);
                        review.setUser(user);
                        reviews.add(review);
                    }
                    onResponse.accept(reviews);
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

    public void read(String id, Consumer<Review> onResponse,
                     Consumer<String> onError, String tag){
        Consumer<JSONObject> responseConsumer = (JSONObject response) -> {
            if (onResponse != null){
                try {
                    Review review = gson.fromJson(response.toString(), Review.class);
                    JSONObject jsonUser = response.getJSONObject("user");
                    User user = gson.fromJson(jsonUser.toString(), User.class);
                    review.setUser(user);
                    onResponse.accept(review);
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
        requestHandler.get(path + id, responseConsumer, errorConsumer, tag);
    }

    public void update(Review review, Consumer<Review> onResponse,
                       Consumer<String> onError, String tag){
        Consumer<JSONObject> responseConsumer = (JSONObject response) -> {
            if (onResponse != null){
                try {
                    Review returnedReview = gson.fromJson(response.toString(), Review.class);
                    JSONObject jsonUser = response.getJSONObject("user");
                    User user = gson.fromJson(jsonUser.toString(), User.class);
                    returnedReview.setUser(user);
                    onResponse.accept(returnedReview);
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
        try {
            JSONObject jsonReview = new JSONObject(gson.toJson(review));
            requestHandler.put(path + review.getId(), jsonReview, responseConsumer, errorConsumer, tag);
        } catch (JSONException exception) {
            Log.e(tag, "ERROR: JSON exception!", exception);
            onError.accept("Connection error");
        }
    }

    public void delete(String id, Consumer<Review> onResponse,
                       Consumer<String> onError, String tag){
        Consumer<JSONObject> responseConsumer = (JSONObject response) -> {
            if (onResponse != null){
                try {
                    Review review = gson.fromJson(response.toString(), Review.class);
                    JSONObject jsonUser = response.getJSONObject("user");
                    User user = gson.fromJson(jsonUser.toString(), User.class);
                    review.setUser(user);
                    onResponse.accept(review);
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
        requestHandler.delete(path + id, responseConsumer, errorConsumer, tag);
    }

    private class ReviewCreateModel {
        private String user;
        private String place;
        private int rating;
        private String comment;

        private ReviewCreateModel(Review review){
            this.user = review.getUser().getId();
            this.place = review.getPlace();
            this.rating = review.getRating();
            this.comment = review.getComment();
        }
    }
}
