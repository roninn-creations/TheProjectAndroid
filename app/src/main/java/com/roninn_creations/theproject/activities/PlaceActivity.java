package com.roninn_creations.theproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.roninn_creations.theproject.R;
import com.roninn_creations.theproject.adapters.ReviewsAdapter;
import com.roninn_creations.theproject.models.Place;
import com.roninn_creations.theproject.models.Review;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.roninn_creations.theproject.TheProjectApplication.getGson;
import static com.roninn_creations.theproject.TheProjectApplication.getRequestHandler;
import static com.roninn_creations.theproject.TheProjectApplication.getReviewsService;

public class PlaceActivity extends AppCompatActivity {

    private static final String TAG = PlaceActivity.class.getName();
    public static final String EXTRA_KEY_PLACE = "EXTRA_KEY_PLACE";

    private Place place;
    private List<Review> reviews;
    private ReviewsAdapter reviewsAdapter;

    private ProgressBar progressBar;
    private TextView nameText;
    private TextView tagsText;
    private TextView addressText;
    private ListView reviewsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        place = getGson().fromJson(getIntent().getStringExtra(EXTRA_KEY_PLACE), Place.class);
        reviews = new ArrayList<>();
        reviewsAdapter = new ReviewsAdapter(this, reviews);

        progressBar = findViewById(R.id.progress_bar);
        nameText = findViewById(R.id.text_name);
        tagsText = findViewById(R.id.text_tags);
        addressText = findViewById(R.id.text_address);
        reviewsList = findViewById(R.id.reviews_list);
        reviewsList.setAdapter(reviewsAdapter);
        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(this::onAddButtonClick);
    }

    @Override
    protected void onStart(){
        super.onStart();
        nameText.setText(place.getName());
        tagsText.setText(Arrays.toString(place.getTags()));
        addressText.setText(place.getAddress().toString());
        progressBar.setVisibility(View.VISIBLE);
        reviewsList.setVisibility(View.GONE);
        getReviewsService().readMany("?place=" + place.getId(),
                this::onGetReviewsResponse, this::onErrorResponse, TAG);
    }

    @Override
    public void onStop(){
        super.onStop();
        getRequestHandler().cancelRequests(TAG);
    }

    private void onAddButtonClick(View view){
        Intent addReviewIntent = new Intent(this, AddReviewActivity.class);
        addReviewIntent.putExtra(AddReviewActivity.EXTRA_KEY_PLACE, getGson().toJson(place));
        startActivity(addReviewIntent);
    }

    private void onGetReviewsResponse(List<Review> reviews){
        this.reviews.clear();
        this.reviews.addAll(reviews);
        reviewsAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
        reviewsList.setVisibility(View.VISIBLE);
    }

    private void onErrorResponse(String message){
        progressBar.setVisibility(View.GONE);
        reviewsList.setVisibility(View.VISIBLE);
        Snackbar.make(reviewsList, message, Snackbar.LENGTH_LONG).show();
    }
}
