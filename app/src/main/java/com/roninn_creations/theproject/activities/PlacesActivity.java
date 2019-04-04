package com.roninn_creations.theproject.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.roninn_creations.theproject.R;
import com.roninn_creations.theproject.models.Place;
import com.roninn_creations.theproject.network.ApiGateway;
import com.roninn_creations.theproject.network.JsonConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlacesActivity extends AppCompatActivity {

    public static final String TAG = "PLACES";

    private RequestQueue requestQueue;
    private Gson gson;
    private ArrayList<Place> places;
    //private PlaceAdapter placeAdapter;

    private Toolbar toolbar;
    private ProgressBar progressBar;
    //private ListView placeList;
    private TextView placesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        requestQueue = ApiGateway.getInstance(this).getRequestQueue();
        gson = JsonConverter.getInstance().getGson();

        places = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //progressBar = this.findViewById(R.id.progress_bar);
        //placeList = this.findViewById(R.id.users_list);

        placesText = findViewById(R.id.places_text);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        printText("Started!");

        //progressBar.setVisibility(View.VISIBLE);
        //placeList.setVisibility(View.GONE);

        //Request for getting the list of all users
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,
                getString(R.string.places_url), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){
                        printList(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w(TAG, "WARNING: Error response!", error);
                        printText("ERROR:" + error.networkResponse.statusCode);
                    }
                })
        {@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> headers = new HashMap<>();
            //headers.put("Content-Type", "application/json");
            headers.put("Authorization",
                    "Bearer " + getString(R.string.test_token));
            return headers;}
        };
        jsonRequest.setTag(TAG);
        requestQueue.add(jsonRequest);

//        StringRequest stringRequest = new StringRequest(Request.Method.GET,
//                getString(R.string.places_url),
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response){
//                        printText(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.w(TAG, "WARNING: Error response!", error);
//                        printText("ERROR:" + error.networkResponse.statusCode);
//                    }
//                })
//        {@Override
//        public Map<String, String> getHeaders() throws AuthFailureError {
//            Map<String, String> headers = new HashMap<>();
//            //headers.put("Content-Type", "application/json");
//            headers.put("Authorization",
//                    "Bearer " + getString(R.string.test_token));
//            return headers;}
//        };
//        stringRequest.setTag(TAG);
//        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_places, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            printText("Pressed!");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void printText(String response) {
        placesText.setText(response);
    }

    private void printList(JSONObject json) {
        JSONArray jsonPlaces = json.optJSONArray("rows");
        places.clear();
        try {
            for (int i = 0; i < jsonPlaces.length(); i++){
                places.add(gson.fromJson(jsonPlaces.getString(i), Place.class));
            }
        }
        catch (JSONException e) {
            String message = "ERROR:" + e.getMessage();
            Log.w(TAG, message);
            placesText.setText(message);
        }
        StringBuilder message = new StringBuilder();
        for (Place place: places) {
            message.append(place.getName()).append(" ");
        }
        placesText.setText(message);
    }
}
