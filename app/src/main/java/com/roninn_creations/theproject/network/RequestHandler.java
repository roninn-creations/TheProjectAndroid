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

    private String url;
    private String token;
    private RequestQueue requestQueue;

    public RequestHandler(String url, String token, RequestQueue requestQueue){
        this.url = url;
        this.token = token;
        this.requestQueue = requestQueue;
    }

    public void get(String path, Consumer<JSONObject> onResponse, String tag){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url + path, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){
                        onResponse.accept(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w(tag, "WARNING: Error response!", error);
                    }
                })
        {@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization",
                    "Bearer " + token);
            return headers;}
        };
        request.setTag(tag);
        requestQueue.add(request);
    }

    public void post(String path, JSONObject body, Consumer<JSONObject> onResponse, String tag){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url + path, body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){
                        onResponse.accept(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w(tag, "WARNING: Error response!", error);
                    }
                })
        {@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("Authorization",
                    "Bearer " + token);
            return headers;}
        };
        request.setTag(tag);
        requestQueue.add(request);
    }

    public void put(String path, JSONObject body, Consumer<JSONObject> onResponse, String tag){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url + path, body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){
                        onResponse.accept(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w(tag, "WARNING: Error response!", error);
                    }
                })
        {@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("Authorization",
                    "Bearer " + token);
            return headers;}
        };
        request.setTag(tag);
        requestQueue.add(request);
    }

    public void delete(String path, Consumer<JSONObject> onResponse, String tag){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url + path, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){
                        onResponse.accept(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w(tag, "WARNING: Error response!", error);
                    }
                })
        {@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization",
                    "Bearer " + token);
            return headers;}
        };
        request.setTag(tag);
        requestQueue.add(request);
    }

    public void getString(String path, Consumer<String> onResponse, String tag){
        StringRequest request = new StringRequest(Request.Method.GET, url + path,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){
                        onResponse.accept(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w(tag, "WARNING: Error response!", error);
                    }
                })
        {@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization",
                    "Bearer " + token);
            return headers;}
        };
        request.setTag(tag);
        requestQueue.add(request);
    }

    public void getArray(String path, Consumer<JSONArray> onResponse, String tag){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url + path, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response){
                        onResponse.accept(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w(tag, "WARNING: Error response!", error);
                    }
                })
        {@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization",
                    "Bearer " + token);
            return headers;}
        };
        request.setTag(tag);
        requestQueue.add(request);
    }

    public void cancelRequests(String tag){
        requestQueue.cancelAll(tag);
    }
}
