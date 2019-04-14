package com.roninn_creations.theproject.services;

import android.util.Log;

import com.google.gson.Gson;
import com.roninn_creations.theproject.models.Review;
import com.roninn_creations.theproject.network.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.roninn_creations.theproject.TheProjectApplication.getGson;

public class ReviewsService extends Service implements IService<Review> {

    public ReviewsService(String path, Gson gson, RequestHandler requestHandler){
        this.path = path;
        this.gson = gson;
        this.requestHandler = requestHandler;
    }

    public void readAll(Consumer<List<Review>> onResponse, String tag){
        Consumer<JSONObject> consumer = new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject response) {
                List<Review> reviews = new ArrayList<>();
                JSONArray jsonReviews = response.optJSONArray("rows");
                try {
                    for (int i = 0; i < jsonReviews.length(); i++)
                        reviews.add(getGson().fromJson(jsonReviews.getString(i), Review.class));
                    onResponse.accept(reviews);
                }
                catch (JSONException e) {
                    String message = "ERROR:" + e.getMessage();
                    Log.w(tag, message);
                }
            }
        };
        requestHandler.get(path, consumer, tag);
    }

    public void read(String id, Consumer<Review> onResponse, String tag){
        Consumer<JSONObject> consumer = new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject response) {
                Review review = gson.fromJson(response.toString(), Review.class);
                onResponse.accept(review);
            }
        };
        requestHandler.get(path + id, consumer, tag);
    }

    public void create(Review review, Consumer<Review> onResponse, String tag){
        Consumer<JSONObject> consumer = new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject response) {
                Review review = gson.fromJson(response.toString(), Review.class);
                onResponse.accept(review);
            }
        };
        try {
            JSONObject jsonReview = new JSONObject(gson.toJson(review));
            requestHandler.post(path, jsonReview, consumer, tag);
        }
        catch (JSONException e) {
            String message = "ERROR:" + e.getMessage();
            Log.w(tag, message);
        }
    }

    public void update(Review review, Consumer<Review> onResponse, String tag){
        Consumer<JSONObject> consumer = new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject response) {
                Review review = gson.fromJson(response.toString(), Review.class);
                onResponse.accept(review);
            }
        };
        try {
            JSONObject jsonReview = new JSONObject(gson.toJson(review));
            requestHandler.put(path + review.getId(), jsonReview, consumer, tag);
        }
        catch (JSONException e) {
            String message = "ERROR:" + e.getMessage();
            Log.w(tag, message);
        }
    }

    public void delete(String id, Consumer<Review> onResponse, String tag){
        Consumer<JSONObject> consumer = new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject response) {
                Review review = gson.fromJson(response.toString(), Review.class);
                onResponse.accept(review);
            }
        };
        requestHandler.delete(path + id, consumer, tag);
    }
}
