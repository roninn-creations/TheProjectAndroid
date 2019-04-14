package com.roninn_creations.theproject.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.roninn_creations.theproject.R;
import com.roninn_creations.theproject.adapters.PlaceAdapter;
import com.roninn_creations.theproject.models.Place;

import java.util.ArrayList;
import java.util.List;

import static com.roninn_creations.theproject.TheProjectApplication.getPlacesService;
import static com.roninn_creations.theproject.TheProjectApplication.getRequestHandler;

public class PlacesActivity extends AppCompatActivity {

    private static final String TAG = PlacesActivity.class.getName();

    private List<Place> places;
    private PlaceAdapter placeAdapter;

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private ListView placesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        places = new ArrayList<>();
        placeAdapter = new PlaceAdapter(this, places);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = this.findViewById(R.id.progress_bar);
        placesList = this.findViewById(R.id.places_list);
        placesList.setAdapter(placeAdapter);

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

        progressBar.setVisibility(View.VISIBLE);
        placesList.setVisibility(View.GONE);

        getPlacesService().readAll(this::updateList, TAG);
    }

    @Override
    public void onStop(){
        super.onStop();

        getRequestHandler().cancelRequests(TAG);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateList(List<Place> places){
        this.places.clear();
        this.places.addAll(places);
        placeAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
        placesList.setVisibility(View.VISIBLE);
    }
}
