package com.roninn_creations.theproject.network;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class RequestHandlerInstrumentedTest {

    private static final String TAG = RequestHandlerInstrumentedTest.class.getName();
    private static final String TEST_URL = "https://jsonplaceholder.typicode.com/";
    private static final String TODOS_PATH = "todos/";
    private static final String TEST_JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVjYzQ3M2QwNWQxYTdjMmNhMDNhYzBkZiIsImV4cCI6MTU2MzYyNzIwMiwiaWF0IjoxNTU4NDQzMjAyfQ.KEZd8j8YABK0ntc1v5DD_lKImhlyBB8uKqAQ2vTA3p0";
    private static final String TEST_USERNAME = "TestUser";
    private static final String TEST_PASSWORD = "TestPassword";

    private static RequestHandler requestHandler;

    private JSONObject jsonResponse;
    private String stringResponse;
    private JSONArray arrayResponse;
    private Boolean response;
    private VolleyError errorResponse;
    private CountDownLatch lock = new CountDownLatch(1);

    @BeforeClass
    public static void classSetUp(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        Volley volley = new Volley(appContext);
        requestHandler = new RequestHandler(TEST_URL,
                volley.getRequestQueue(), TEST_JWT);
    }

    @Before
    public void setUp() {
        jsonResponse = null;
        stringResponse = null;
        arrayResponse = null;
        response = false;
        errorResponse = null;
    }

    @Test
    public void get() {
        Consumer<JSONObject> responseConsumer = (JSONObject response) -> {
            jsonResponse = response;
        };
        Consumer<VolleyError> errorConsumer = (VolleyError error) -> {
            errorResponse = error;
        };

        requestHandler.get(TODOS_PATH + 1, responseConsumer, errorConsumer, TAG);

        try {
            lock.await(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            fail("InterruptedException");
        }

        assertNull(errorResponse);
        assertNotNull(jsonResponse);
    }

    @Test
    public void post() {
        Consumer<JSONObject> responseConsumer = (JSONObject response) -> {
            jsonResponse = response;
        };
        Consumer<VolleyError> errorConsumer = (VolleyError error) -> {
            errorResponse = error;
        };

        requestHandler.post(TODOS_PATH, null, responseConsumer, errorConsumer, TAG);

        try {
            lock.await(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            fail("InterruptedException");
        }

        assertNull(errorResponse);
        assertNotNull(jsonResponse);
    }

    @Test
    public void put() {
        Consumer<JSONObject> responseConsumer = (JSONObject response) -> {
            jsonResponse = response;
        };
        Consumer<VolleyError> errorConsumer = (VolleyError error) -> {
            errorResponse = error;
        };

        requestHandler.put(TODOS_PATH + 1, null, responseConsumer, errorConsumer, TAG);

        try {
            lock.await(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            fail("InterruptedException");
        }

        assertNull(errorResponse);
        assertNotNull(jsonResponse);
    }

    @Test
    public void delete() {
        Runnable responseConsumer = () -> {
            response = true;
        };
        Consumer<VolleyError> errorConsumer = (VolleyError error) -> {
            errorResponse = error;
        };

        requestHandler.delete(TODOS_PATH + 1, responseConsumer, errorConsumer, TAG);

        try {
            lock.await(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            fail("InterruptedException");
        }

        assertNull(errorResponse);
        assertTrue(response);
    }

    @Test
    public void getString() {
        Consumer<String> responseConsumer = (String response) -> {
            stringResponse = response;
        };
        Consumer<VolleyError> errorConsumer = (VolleyError error) -> {
            errorResponse = error;
        };

        requestHandler.getString(TODOS_PATH, responseConsumer, errorConsumer, TAG);

        try {
            lock.await(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            fail("InterruptedException");
        }

        assertNull(errorResponse);
        assertNotNull(stringResponse);
    }

    @Test
    public void getArray() {
        Consumer<JSONArray> responseConsumer = (JSONArray response) -> {
            arrayResponse = response;
        };
        Consumer<VolleyError> errorConsumer = (VolleyError error) -> {
            errorResponse = error;
        };

        requestHandler.getArray(TODOS_PATH, responseConsumer, errorConsumer, TAG);

        try {
            lock.await(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            fail("InterruptedException");
        }

        assertNull(errorResponse);
        assertNotNull(arrayResponse);
    }

    @Test
    public void getBasic() {
        Consumer<JSONObject> responseConsumer = (JSONObject response) -> {
            jsonResponse = response;
        };
        Consumer<VolleyError> errorConsumer = (VolleyError error) -> {
            errorResponse = error;
        };

        requestHandler.getBasic(TODOS_PATH + 1, responseConsumer,
                errorConsumer, TEST_USERNAME, TEST_PASSWORD, TAG);

        try {
            lock.await(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            fail("InterruptedException");
        }

        assertNull(errorResponse);
        assertNotNull(jsonResponse);
    }

    @AfterClass
    public static void classTearDown(){
        requestHandler.cancelRequests(TAG);
    }
}