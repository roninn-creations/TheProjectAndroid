package com.roninn_creations.theproject.network;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class VolleyInstrumentedTest {

    private static final String ERROR_MESSAGE = "ERROR";

    private CountDownLatch lock = new CountDownLatch(1);
    private String receivedResponse;

    @Test
    public void constructorAndGetRequestQueue_areCorrect() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Volley volley = new Volley(appContext);
        StringRequest request = new StringRequest(Request.Method.GET, "http://google.com",
                (String response) -> {
                    receivedResponse = response;
                    lock.countDown();
                },
                (VolleyError error) -> {
                    receivedResponse = ERROR_MESSAGE;
                    lock.countDown();
                });
        volley.getRequestQueue().add(request);

        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            fail("InterruptedException");
        }

        assertNotNull(receivedResponse);
        assertNotEquals(receivedResponse, ERROR_MESSAGE);
    }
}
