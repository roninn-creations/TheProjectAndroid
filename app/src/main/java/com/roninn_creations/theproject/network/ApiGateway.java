package com.roninn_creations.theproject.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;

import static com.android.volley.toolbox.Volley.newRequestQueue;

/***
 * From: https://developer.android.com/training/volley/requestqueue
 */
public class ApiGateway {
    private static ApiGateway instance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private ApiGateway(Context context) {
        requestQueue = newRequestQueue(context.getApplicationContext());

        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized ApiGateway getInstance(Context context) {
        if (instance == null) {
            instance = new ApiGateway(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
