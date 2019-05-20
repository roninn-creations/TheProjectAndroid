package com.roninn_creations.theproject.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlaceTest {

    private final static String ID = "11111111";
    private final static String NAME = "Test Place";
    private final static String STREET = "Test Street 1.";
    private final static String POST = "1111";
    private final static String CITY = "Test City";
    private final static String[] TAGS = {"tag1", "tag2", "tag3"};

    @Test
    public void constructorAndGetters_areCorrect() {
        Place place = new Place(ID, NAME, STREET, POST, CITY, TAGS);

        assertEquals(place.getId(), ID);
        assertEquals(place.getName(), NAME);
        assertEquals(place.getAddress().getStreet(), STREET);
        assertEquals(place.getAddress().getPost(), POST);
        assertEquals(place.getAddress().getCity(), CITY);
        assertArrayEquals(place.getTags(), TAGS);
    }

    @Test
    public void setters_areCorrect() {
        Place place = new Place(null, null, null, null, null, null);
        place.setId(ID);
        place.setName(NAME);
        place.getAddress().setStreet(STREET);
        place.getAddress().setPost(POST);
        place.getAddress().setCity(CITY);
        place.setTags(TAGS);

        assertEquals(place.getId(), ID);
        assertEquals(place.getName(), NAME);
        assertEquals(place.getAddress().getStreet(), STREET);
        assertEquals(place.getAddress().getPost(), POST);
        assertEquals(place.getAddress().getCity(), CITY);
        assertArrayEquals(place.getTags(), TAGS);
    }
}
