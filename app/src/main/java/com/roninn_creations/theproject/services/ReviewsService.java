package com.roninn_creations.theproject.services;

import android.util.Log;

import com.google.gson.Gson;
import com.roninn_creations.theproject.models.Review;
import com.roninn_creations.theproject.models.User;
import com.roninn_creations.theproject.network.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ReviewsService extends Service implements IService<Review> {

    public ReviewsService(String path, Gson gson, RequestHandler requestHandler){
        super(path, gson, requestHandler);
    }

    public void readAll(Consumer<List<Review>> onResponse, String tag){
        Consumer<JSONObject> consumer = new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject response) {
                try {
                    List<Review> reviews = new ArrayList<>();
                    JSONArray jsonReviews = response.optJSONArray("rows");
                    for (int i = 0; i < jsonReviews.length(); i++){
                        JSONObject jsonReview = jsonReviews.getJSONObject(i);
                        Review review = gson.fromJson(jsonReview.toString(), Review.class);
                        JSONObject jsonUser = jsonReview.getJSONObject("user");
                        User user = gson.fromJson(jsonUser.toString(), User.class);
                        review.setUser(user);
                        reviews.add(review);
                    }
                    if (onResponse != null)
                        onResponse.accept(reviews);
                } catch (JSONException e) {
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
                try {
                    Review review = gson.fromJson(response.toString(), Review.class);
                    JSONObject jsonUser = response.getJSONObject("user");
                    User user = gson.fromJson(jsonUser.toString(), User.class);
                    review.setUser(user);
                    if (onResponse != null)
                        onResponse.accept(review);
                } catch (JSONException e) {
                    String message = "ERROR:" + e.getMessage();
                    Log.w(tag, message);
                }
            }
        };
        requestHandler.get(path + id, consumer, tag);
    }

    public void create(Review review, Consumer<Review> onResponse, String tag){
        Consumer<JSONObject> consumer = new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject response) {
                try {
                    Review review = gson.fromJson(response.toString(), Review.class);
                    JSONObject jsonUser = response.getJSONObject("user");
                    User user = gson.fromJson(jsonUser.toString(), User.class);
                    review.setUser(user);
                    if (onResponse != null)
                        onResponse.accept(review);
                } catch (JSONException e) {
                    String message = "ERROR:" + e.getMessage();
                    Log.w(tag, message);
                }
            }
        };
        try {
            ReviewCreateModel createModel = new ReviewCreateModel(review.getUser().getId(),
                    review.getPlace().getId(),
                    review.getRating(),
                    review.getComment());
            JSONObject jsonReview = new JSONObject(gson.toJson(createModel));
            requestHandler.post(path, jsonReview, consumer, tag);
        } catch (JSONException e) {
            String message = "ERROR:" + e.getMessage();
            Log.w(tag, message);
        }
    }

    public void update(Review review, Consumer<Review> onResponse, String tag){
        Consumer<JSONObject> consumer = new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject response) {
                try {
                    Review review = gson.fromJson(response.toString(), Review.class);
                    JSONObject jsonUser = response.getJSONObject("user");
                    User user = gson.fromJson(jsonUser.toString(), User.class);
                    review.setUser(user);
                    if (onResponse != null)
                        onResponse.accept(review);
                } catch (JSONException e) {
                    String message = "ERROR:" + e.getMessage();
                    Log.w(tag, message);
                }
            }
        };
        try {
            JSONObject jsonReview = new JSONObject(gson.toJson(review));
            requestHandler.put(path + review.getId(), jsonReview, consumer, tag);
        } catch (JSONException e) {
            String message = "ERROR:" + e.getMessage();
            Log.w(tag, message);
        }
    }

    public void delete(String id, Consumer<Review> onResponse, String tag){
        Consumer<JSONObject> consumer = new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject response) {
                try {
                    Review review = gson.fromJson(response.toString(), Review.class);
                    JSONObject jsonUser = response.getJSONObject("user");
                    User user = gson.fromJson(jsonUser.toString(), User.class);
                    review.setUser(user);
                    if (onResponse != null)
                        onResponse.accept(review);
                } catch (JSONException e) {
                    String message = "ERROR:" + e.getMessage();
                    Log.w(tag, message);
                }
            }
        };
        requestHandler.delete(path + id, consumer, tag);
    }

    private class ReviewCreateModel {
        private String user;
        private String place;
        private int rating;
        private String comment;

        public ReviewCreateModel(String user, String place, int rating, String comment) {
            this.user = user;
            this.place = place;
            this.rating = rating;
            this.comment = comment;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }
}
