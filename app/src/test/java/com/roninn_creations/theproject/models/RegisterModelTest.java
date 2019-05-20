package com.roninn_creations.theproject.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class RegisterModelTest {

    private final static String EMAIL = "test@mail.com";
    private final static String NAME = "Test User";
    private final static String PICTURE = "http://address/picture.png";
    private final static String PASSWORD = "Test P@ssw0rd";

    @Test
    public void constructorAndGetters_areCorrect() {
        RegisterModel registerModel = new RegisterModel(EMAIL, NAME, PICTURE, PASSWORD);

        assertEquals(registerModel.getEmail(), EMAIL);
        assertEquals(registerModel.getName(), NAME);
        assertEquals(registerModel.getPicture(), PICTURE);
        assertEquals(registerModel.getPassword(), PASSWORD);
    }

    @Test
    public void setters_areCorrect() {
        RegisterModel registerModel = new RegisterModel(null, null, null, null);
        registerModel.setEmail(EMAIL);
        registerModel.setName(NAME);
        registerModel.setPicture(PICTURE);
        registerModel.setPassword(PASSWORD);

        assertEquals(registerModel.getEmail(), EMAIL);
        assertEquals(registerModel.getName(), NAME);
        assertEquals(registerModel.getPicture(), PICTURE);
        assertEquals(registerModel.getPassword(), PASSWORD);
    }
}