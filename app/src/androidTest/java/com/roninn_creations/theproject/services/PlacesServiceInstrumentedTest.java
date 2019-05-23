package com.roninn_creations.theproject.services;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roninn_creations.theproject.models.Place;
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

public class PlacesServiceInstrumentedTest {

    private static final String TAG = PlacesServiceInstrumentedTest.class.getName();
    private static final String DATE_FORMAT = "yyyy-MM-dd\\'T\\'HH:mm:ss.SSS\\'Z\\'";
    private static final String BASE_URL = "https://the-project-api.herokuapp.com/";
    private static final String PLACES_PATH = "places/";
    private static final String TEST_JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVjYzQ3M2QwNWQxYTdjMmNhMDNhYzBkZiIsImV4cCI6MTU2MzYyNzIwMiwiaWF0IjoxNTU4NDQzMjAyfQ.KEZd8j8YABK0ntc1v5DD_lKImhlyBB8uKqAQ2vTA3p0";
    private static final String TEST_PLACE_NAME = "Test Place";
    private static final String NEW_PLACE_NAME = "New Place";
    private static final String TEST_PLACE_STREET = "Test Street 1.";
    private static final String TEST_PLACE_POST = "1111";
    private static final String TEST_PLACE_CITY = "Test City";
    private static final String TEST_PLACE_TAG1 = "tag1";
    private static final String TEST_PLACE_TAG2 = "tag2";

    private static RequestHandler requestHandler;
    private static PlacesService placesService;

    private Place testPlace;
    private Place newPlace;
    private Place placeResponse;
    private List<Place> placesResponse;
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
        placesService = new PlacesService(PLACES_PATH, gson, requestHandler);
    }

    @Before
    public void setUp() throws InterruptedException{
        String[] tags = {TEST_PLACE_TAG1, TEST_PLACE_TAG2};
        testPlace = new Place(null, TEST_PLACE_NAME, TEST_PLACE_STREET,
                TEST_PLACE_POST, TEST_PLACE_CITY, tags);

        placesService.create(testPlace, (Place place) -> {
            placeResponse = place;
        }, null, TAG);

        lock.await(1000, TimeUnit.MILLISECONDS);

        testPlace.setId(placeResponse.getId());

        placeResponse = null;
        placesResponse = null;
        response = false;
        errorMessage = null;
    }

    @Test
    public void create() {
        String[] tags = {TEST_PLACE_TAG1, TEST_PLACE_TAG2};
        newPlace = new Place(null, NEW_PLACE_NAME, TEST_PLACE_STREET,
                TEST_PLACE_POST, TEST_PLACE_CITY, tags);

        placesService.create(newPlace,
                (Place place) -> {
                    placeResponse = place;
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
        assertNotNull(placeResponse);

        newPlace.setId(placeResponse.getId());
    }

    @Test
    public void readMany() {
        placesService.readMany("",
                (List<Place> places) -> {
                    placesResponse = places;
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
        assertNotNull(placesResponse);
    }

    @Test
    public void read() {
        placesService.read(testPlace.getId(),
                (Place place) -> {
                    placeResponse = place;
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
        assertNotNull(placeResponse);
    }

    @Test
    public void update() {
        testPlace.setName(NEW_PLACE_NAME);

        placesService.update(testPlace,
                (Place place) -> {
                    placeResponse = place;
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
        assertNotNull(placeResponse);
    }

//    @Test
//    public void delete() {
//        placesService.delete(testPlace.getId(),
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
//        //assertNull(errorMessage);
//        assertTrue(response);
//    }

    @After
    public void tearDown() throws InterruptedException{
                placesService.delete(testPlace.getId(), null,
                        (String message) -> {
                            errorMessage = message;
                        }, TAG);

        if (newPlace != null)
            placesService.delete(newPlace.getId(), null, null, TAG);

        lock.await(1000, TimeUnit.MILLISECONDS);

        placeResponse = null;
        placesResponse = null;
        response = false;
        errorMessage = null;
    }

    @AfterClass
    public static void classTearDown(){
        requestHandler.cancelRequests(TAG);
    }
}
