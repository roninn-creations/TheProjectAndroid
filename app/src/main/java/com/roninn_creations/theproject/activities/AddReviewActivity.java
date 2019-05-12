package com.roninn_creations.theproject.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.roninn_creations.theproject.R;
import com.roninn_creations.theproject.models.Place;
import com.roninn_creations.theproject.models.Review;

import static com.roninn_creations.theproject.TheProjectApplication.getGson;
import static com.roninn_creations.theproject.TheProjectApplication.getRequestHandler;
import static com.roninn_creations.theproject.TheProjectApplication.getReviewsService;
import static com.roninn_creations.theproject.TheProjectApplication.getUser;

public class AddReviewActivity extends AppCompatActivity {

    private static final String TAG = AddReviewActivity.class.getName();
    public static final String EXTRA_KEY_PLACE = "EXTRA_KEY_PLACE";

    private Place place;

    private ProgressBar progressBar;
    private RadioButton oneRadio;
    private RadioButton twoRadio;
    private RadioButton threeRadio;
    private RadioButton fourRadio;
    private RadioButton fiveRadio;
    private EditText commentEdit;
    private Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        place = getGson().fromJson(getIntent().getStringExtra(EXTRA_KEY_PLACE), Place.class);

        progressBar = findViewById(R.id.progress_bar);
        oneRadio = findViewById(R.id.radio_one);
        twoRadio = findViewById(R.id.radio_two);
        threeRadio = findViewById(R.id.radio_three);
        fourRadio = findViewById(R.id.radio_four);
        fiveRadio = findViewById(R.id.radio_five);
        commentEdit = findViewById(R.id.edit_comment);
        saveButton = findViewById(R.id.button_save);

        commentEdit.setOnEditorActionListener(this::onCommentEditorSend);
        saveButton.setOnClickListener(this::onSaveButtonClick);
    }

    @Override
    public void onStop(){
        super.onStop();
        getRequestHandler().cancelRequests(TAG);
    }

    private void onSaveButtonClick(View view){
        submitReview();
    }

    private boolean onCommentEditorSend(TextView view, int actionId, KeyEvent event){
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            submitReview();
            handled = true;
        }
        return handled;
    }

    private void submitReview(){
        int rating;
        if (oneRadio.isChecked())
            rating = 1;
        else if (twoRadio.isChecked())
            rating = 2;
        else if (threeRadio.isChecked())
            rating = 3;
        else if (fourRadio.isChecked())
            rating = 4;
        else if (fiveRadio.isChecked())
            rating = 5;
        else
            rating = 0;
        String comment = commentEdit.getText().toString();
        Review review = new Review(null, getUser(), place.getId(), rating, comment, null);
        getReviewsService().create(review,
                this::onCreateResponse, this::onErrorResponse, TAG);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void onCreateResponse(Review review) {
        Intent placeIntent = new Intent(this, PlaceActivity.class);
        placeIntent.putExtra(PlaceActivity.EXTRA_KEY_PLACE, getGson().toJson(place));
        startActivity(placeIntent);
    }

    private void onErrorResponse(String message){
        progressBar.setVisibility(View.GONE);
        Snackbar.make(saveButton, message, Snackbar.LENGTH_LONG).show();
    }
}
