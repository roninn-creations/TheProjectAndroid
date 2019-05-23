package com.roninn_creations.theproject.network;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class VolleyInstrumentedTest {

    private static Volley volley;

    @BeforeClass
    public static void classSetUp(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        volley = new Volley(appContext);
    }

    @Test
    public void getRequestQueue() {
        RequestQueue requestQueue = volley.getRequestQueue();

        assertNotNull(requestQueue);
    }

    @Test
    public void getImageLoader() {
        ImageLoader imageLoader = volley.getImageLoader();

        assertNotNull(imageLoader);
    }
}
