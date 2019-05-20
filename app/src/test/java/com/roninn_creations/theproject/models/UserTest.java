package com.roninn_creations.theproject.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    private final static String ID = "11111111";
    private final static String NAME = "Test User";
    private final static String PICTURE = "http://address/picture.png";

    @Test
    public void constructorAndGetters_areCorrect() {
        User user = new User(ID, NAME, PICTURE);

        assertEquals(user.getId(), ID);
        assertEquals(user.getName(), NAME);
        assertEquals(user.getPicture(), PICTURE);
    }

    @Test
    public void setters_areCorrect() {
        User user = new User(null, null, null);
        user.setId(ID);
        user.setName(NAME);
        user.setPicture(PICTURE);

        assertEquals(user.getId(), ID);
        assertEquals(user.getName(), NAME);
        assertEquals(user.getPicture(), PICTURE);
    }
}
