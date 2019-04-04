package com.roninn_creations.theproject.models;

import java.util.Date;

public class Review {

    private String id;
    private User user;
    private Place place;
    private Date createdAt;

    public Review(String id, User user, Place place, Date createdAt) {
        this.id = id;
        this.user = user;
        this.place = place;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
