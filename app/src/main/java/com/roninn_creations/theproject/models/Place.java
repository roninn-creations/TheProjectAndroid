package com.roninn_creations.theproject.models;

public class Place {

    private String id;
    private String name;
    private String address;
    private String category;

    public Place(String id, String name, String address, String category) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
