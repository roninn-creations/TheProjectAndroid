package com.roninn_creations.theproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import com.roninn_creations.theproject.R;
import com.roninn_creations.theproject.adapters.PlacesAdapter;
import com.roninn_creations.theproject.models.Place;
import com.roninn_creations.theproject.views.NonScrollListView;

import java.util.ArrayList;
import java.util.List;

import static com.roninn_creations.theproject.TheProjectApplication.getGson;
import static com.roninn_creations.theproject.TheProjectApplication.getPlacesService;
import static com.roninn_creations.theproject.TheProjectApplication.getRequestHandler;

public class PlacesActivity extends AppCompatActivity{

    private static final String TAG = PlacesActivity.class.getName();

    private List<Place> places;
    private PlacesAdapter placesAdapter;

    private SearchView searchView;
    private ProgressBar progressBar;
    private NonScrollListView placesList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        places = new ArrayList<>();
        placesAdapter = new PlacesAdapter(this, places);

        searchView = findViewById(R.id.search);
        progressBar = findViewById(R.id.progress_bar);
        placesList = findViewById(R.id.list_places);
        placesList.setAdapter(placesAdapter);
        placesList.setOnItemClickListener(this::onPlacesItemClick);
        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(this::onAddButtonClick);

        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void onStart(){
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
        placesList.setVisibility(View.GONE);
        getPlacesService().readMany("",
                this::onGetPlacesResponse, this::onErrorResponse, TAG);
    }

    @Override
    protected void onResume(){
        super.onResume();
        searchView.setQuery("", false);
    }

    @Override
    public void onStop(){
        super.onStop();
        getRequestHandler().cancelRequests(TAG);
    }

    public void onPlacesItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent placeIntent = new Intent(this, PlaceActivity.class);
        placeIntent.putExtra(PlaceActivity.EXTRA_KEY_PLACE, getGson().toJson(places.get(position)));
        startActivity(placeIntent);
    }

    private void onAddButtonClick(View view){
        Intent addPlaceIntent = new Intent(this, AddPlaceActivity.class);
        startActivity(addPlaceIntent);
    }

    private void onGetPlacesResponse(List<Place> places){
        this.places.clear();
        this.places.addAll(places);
        placesAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
        placesList.setVisibility(View.VISIBLE);
    }

    private void onErrorResponse(String message){
        progressBar.setVisibility(View.GONE);
        placesList.setVisibility(View.VISIBLE);
        Snackbar.make(placesList, message, Snackbar.LENGTH_LONG).show();
    }
}
