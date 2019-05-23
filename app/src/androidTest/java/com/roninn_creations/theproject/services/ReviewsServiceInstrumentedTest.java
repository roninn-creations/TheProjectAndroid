package com.roninn_creations.theproject.services;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roninn_creations.theproject.models.Review;
import com.roninn_creations.theproject.models.User;
import com.roninn_creations.theproject.network.RequestHandler;
import com.roninn_creations.theproject.network.Volley;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ReviewsServiceInstrumentedTest {

    private static final String TAG = ReviewsServiceInstrumentedTest.class.getName();
    private static final String DATE_FORMAT = "yyyy-MM-dd\\'T\\'HH:mm:ss.SSS\\'Z\\'";
    private static final String BASE_URL = "https://the-project-api.herokuapp.com/";
    private static final String PLACES_PATH = "reviews/";
    private static final String TEST_JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVjYzQ3M2QwNWQxYTdjMmNhMDNhYzBkZiIsImV4cCI6MTU2MzYyNzIwMiwiaWF0IjoxNTU4NDQzMjAyfQ.KEZd8j8YABK0ntc1v5DD_lKImhlyBB8uKqAQ2vTA3p0";
    private static final String TEST_REVIEW_USER_ID = "5cd1a26681efe8306cd6afaf";
    private static final String TEST_REVIEW_PLACE_ID = "5cd1a26681efe8306cd6afaf";
    private static final int TEST_REVIEW_RATING = 3;
    private static final int NEW_REVIEW_RATING = 4;
    private static final String TEST_REVIEW_COMMENT = "Some comment.";

    private static RequestHandler requestHandler;
    private static ReviewsService reviewsService;

    private Review testReview;
    private Review newReview;
    private Review reviewResponse;
    private List<Review> reviewsResponse;
    private Boolean response;
    private String errorMessage;
    private CountDownLatch lock = new CountDownLatch(1);

    @BeforeClass
    public static void classSetUp(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT).create();
        Gson gson = gsonBuilder.create();
        Volley volley = new Volley(appContext);
        requestHandler = new RequestHandler(BASE_URL,
                volley.getRequestQueue(), TEST_JWT);
        reviewsService = new ReviewsService(PLACES_PATH, gson, requestHandler);
    }

    @Before
    public void setUp() throws InterruptedException{
        User testUser = new User(TEST_REVIEW_USER_ID, null, null);
        testReview = new Review(null, testUser,
                TEST_REVIEW_PLACE_ID, TEST_REVIEW_RATING, TEST_REVIEW_COMMENT, null);

        reviewsService.create(testReview, (Review review) -> {
            reviewResponse = review;
        }, null, TAG);

        lock.await(1000, TimeUnit.MILLISECONDS);

        testReview.setId(reviewResponse.getId());

        reviewResponse = null;
        reviewsResponse = null;
        response = false;
        errorMessage = null;
    }

    @Test
    public void create() {
        User testUser = new User(TEST_REVIEW_USER_ID, null, null);
        newReview = new Review(null, testUser,
                TEST_REVIEW_PLACE_ID, TEST_REVIEW_RATING, TEST_REVIEW_COMMENT, null);

        reviewsService.create(newReview,
                (Review review) -> {
                    reviewResponse = review;
                },
                (String message) -> {
                    errorMessage = message;
                },
                TAG);

        try {
            lock.await(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            fail("InterruptedException");
        }

        assertNull(errorMessage);
        assertNotNull(reviewResponse);

        newReview.setId(reviewResponse.getId());
    }

    @Test
    public void readMany() {
        reviewsService.readMany("",
                (List<Review> reviews) -> {
                    reviewsResponse = reviews;
                },
                (String message) -> {
                    errorMessage = message;
                },
                TAG);

        try {
            lock.await(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            fail("InterruptedException");
        }

        assertNull(errorMessage);
        assertNotNull(reviewsResponse);
    }

    @Test
    public void read() {
        reviewsService.read(testReview.getId(),
                (Review review) -> {
                    reviewResponse = review;
                },
                (String message) -> {
                    errorMessage = message;
                },
                TAG);

        try {
            lock.await(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            fail("InterruptedException");
        }

        assertNull(errorMessage);
        assertNotNull(reviewResponse);
    }

    @Test
    public void update() {
        testReview.setRating(NEW_REVIEW_RATING);

        reviewsService.update(testReview,
                (Review review) -> {
                    reviewResponse = review;
                },
                (String message) -> {
                    errorMessage = message;
                },
                TAG);

        try {
            lock.await(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            fail("InterruptedException");
        }

        assertNull(errorMessage);
        assertNotNull(reviewResponse);
    }

//    @Test
//    public void delete() {
//        reviewsService.delete(testReview.getId(),
//                () -> {
//                    response = true;
//                },
//                (String message) -> {
//                    errorMessage = message;
//                }, TAG);
//
//        try {
//            lock.await(1000, TimeUnit.MILLISECONDS);
//        } catch (InterruptedException e) {
//            fail("InterruptedException");
//        }
//
//        assertNull(errorMessage);
//        assertTrue(response);
//    }

    @After
    public void tearDown() throws InterruptedException{
        reviewsService.delete(testReview.getId(), null, null, TAG);

        if (newReview != null)
            reviewsService.delete(newReview.getId(), null, null, TAG);

        lock.await(1000, TimeUnit.MILLISECONDS);

        reviewResponse = null;
        reviewsResponse = null;
        response = false;
        errorMessage = null;
    }

    @AfterClass
    public static void classTearDown(){
        requestHandler.cancelRequests(TAG);
    }
}
