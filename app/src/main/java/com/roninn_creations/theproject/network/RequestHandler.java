package com.roninn_creations.theproject.network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class RequestHandler{

    private final String url;
    private final String token;
    private final RequestQueue requestQueue;

    public RequestHandler(String url, String token, RequestQueue requestQueue){
        this.url = url;
        this.token = token;
        this.requestQueue = requestQueue;
    }

    public void get(String path, Consumer<JSONObject> onResponse, Consumer<VolleyError> onError, String tag){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url + path, null,
                (JSONObject response) -> {
                    if (onResponse != null)
                        onResponse.accept(response);
                },
                (VolleyError error) -> {
                    if (onError != null)
                        onError.accept(error);
                })
        {@Override
        public Map<String, String> getHeaders(){
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + token);
            return headers;}
        };
        request.setTag(tag);
        requestQueue.add(request);
    }

    public void post(String path, JSONObject body, Consumer<JSONObject> onResponse, Consumer<VolleyError> onError, String tag){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url + path, body,
                (JSONObject response) -> {
                    if (onResponse != null)
                        onResponse.accept(response);
                },
                (VolleyError error) -> {
                    if (onError != null)
                        onError.accept(error);
                })
        {@Override
        public Map<String, String> getHeaders(){
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("Authorization", "Bearer " + token);
            return headers;}
        };
        request.setTag(tag);
        requestQueue.add(request);
    }

    public void put(String path, JSONObject body, Consumer<JSONObject> onResponse, Consumer<VolleyError> onError, String tag){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url + path, body,
                (JSONObject response) -> {
                    if (onResponse != null)
                        onResponse.accept(response);
                },
                (VolleyError error) -> {
                    if (onError != null)
                        onError.accept(error);
                })
        {@Override
        public Map<String, String> getHeaders(){
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("Authorization", "Bearer " + token);
            return headers;}
        };
        request.setTag(tag);
        requestQueue.add(request);
    }

    public void delete(String path, Consumer<JSONObject> onResponse, Consumer<VolleyError> onError, String tag){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url + path, null,
                (JSONObject response) -> {
                    if (onResponse != null)
                        onResponse.accept(response);
                },
                (VolleyError error) -> {
                    if (onError != null)
                        onError.accept(error);
                })
        {@Override
        public Map<String, String> getHeaders(){
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + token);
            return headers;}
        };
        request.setTag(tag);
        requestQueue.add(request);
    }

    public void getString(String path, Consumer<String> onResponse, Consumer<VolleyError> onError, String tag){
        StringRequest request = new StringRequest(Request.Method.GET, url + path,
                (String response) -> {
                    if (onResponse != null)
                        onResponse.accept(response);
                },
                (VolleyError error) -> {
                    if (onError != null)
                        onError.accept(error);
                })
        {@Override
        public Map<String, String> getHeaders(){
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + token);
            return headers;}
        };
        request.setTag(tag);
        requestQueue.add(request);
    }

    public void getArray(String path, Consumer<JSONArray> onResponse, Consumer<VolleyError> onError, String tag){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url + path, null,
                (JSONArray response) -> {
                    if (onResponse != null)
                        onResponse.accept(response);
                },
                (VolleyError error) -> {
                    if (onError != null)
                        onError.accept(error);
                })
        {@Override
        public Map<String, String> getHeaders(){
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + token);
            return headers;}
        };
        request.setTag(tag);
        requestQueue.add(request);
    }

    public void cancelRequests(String tag){
        requestQueue.cancelAll(tag);
    }
}
