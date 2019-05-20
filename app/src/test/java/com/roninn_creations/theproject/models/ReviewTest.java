package com.roninn_creations.theproject.models;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class ReviewTest {

    private final static String ID = "11111111";
    private final static User USER = new User("22222222", "Test User", "http://address/picture.png");
    private final static String PLACE = "33333333";
    private final static int RATING = 3;
    private final static String COMMENT = "Some comment.";
    private final static Date CREATED_AT = new Date();

    @Test
    public void constructorAndGetters_areCorrect() {
        Review review = new Review(ID, USER, PLACE, RATING, COMMENT, CREATED_AT);

        assertEquals(review.getId(), ID);
        assertEquals(review.getUser(), USER);
        assertEquals(review.getPlace(), PLACE);
        assertEquals(review.getRating(), RATING);
        assertEquals(review.getComment(), COMMENT);
        assertEquals(review.getCreatedAt(), CREATED_AT);
    }

    @Test
    public void setters_areCorrect() {
        Review review = new Review(null, null, null, 0, null, null);
        review.setId(ID);
        review.setUser(USER);
        review.setPlace(PLACE);
        review.setRating(RATING);
        review.setComment(COMMENT);
        review.setCreatedAt(CREATED_AT);

        assertEquals(review.getId(), ID);
        assertEquals(review.getUser(), USER);
        assertEquals(review.getPlace(), PLACE);
        assertEquals(review.getRating(), RATING);
        assertEquals(review.getComment(), COMMENT);
        assertEquals(review.getCreatedAt(), CREATED_AT);
    }
}
