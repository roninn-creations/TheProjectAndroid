package com.roninn_creations.theproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.roninn_creations.theproject.R;
import com.roninn_creations.theproject.TheProjectApplication;
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

    private ProgressBar progressBar;
    private SearchView searchView;
    private NonScrollListView placesList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        places = new ArrayList<>();
        placesAdapter = new PlacesAdapter(this, places);

        Toolbar toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progress_bar);
        searchView = findViewById(R.id.search);
        ImageView closeButton = searchView.findViewById(R.id.search_close_btn);
        placesList = findViewById(R.id.list_places);
        FloatingActionButton fab = findViewById(R.id.fab_add);

        setSupportActionBar(toolbar);
        searchView.setOnQueryTextListener(new OnQueryTextListener());
        closeButton.setOnClickListener(this::onSearchCloseButtonClick);
        placesList.setAdapter(placesAdapter);
        placesList.setOnItemClickListener(this::onPlacesItemClick);
        fab.setOnClickListener(this::onAddButtonClick);
    }

    @Override
    protected void onStart(){
        super.onStart();
        submitQuery(searchView.getQuery().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shared, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            ((TheProjectApplication)getApplication()).logOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop(){
        super.onStop();
        getRequestHandler().cancelRequests(TAG);
    }

    private void onSearchCloseButtonClick(View view){
        searchView.setQuery("", false);
        submitQuery("");
    }

    private void onPlacesItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent placeIntent = new Intent(this, PlaceActivity.class);
        placeIntent.putExtra(PlaceActivity.EXTRA_KEY_PLACE, getGson().toJson(places.get(position)));
        startActivity(placeIntent);
    }

    private void onAddButtonClick(View view){
        Intent addPlaceIntent = new Intent(this, AddPlaceActivity.class);
        startActivity(addPlaceIntent);
    }

    private void submitQuery(String query){
        progressBar.setVisibility(View.VISIBLE);
        query = (query != null && !query.equals("")) ? "?q=" + query : "";
        getPlacesService().readMany(query,
                this::onGetPlacesResponse, this::onErrorResponse, TAG);
        searchView.clearFocus();
    }

    private void onGetPlacesResponse(List<Place> places){
        this.places.clear();
        this.places.addAll(places);
        placesAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

    private void onErrorResponse(String message){
        progressBar.setVisibility(View.GONE);
        Snackbar.make(placesList, message, Snackbar.LENGTH_LONG).show();
    }

    private class OnQueryTextListener implements SearchView.OnQueryTextListener {
        @Override
        public boolean onQueryTextSubmit(String query) {
            submitQuery(query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String query) {
            return true;
        }
    }
}
