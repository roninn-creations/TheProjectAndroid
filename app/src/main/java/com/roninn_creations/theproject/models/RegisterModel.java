package com.roninn_creations.theproject.models;

public class RegisterModel {

    private String email;
    private String name;
    private String picture;
    private String password;

    public RegisterModel(String email, String name, String password, String picture) {
        this.email = email;
        this.name = name;
        this.picture = picture;
        this.password = password;
    }

    public RegisterModel(String email, String name, String password) {
        this(email, name, password, null);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
