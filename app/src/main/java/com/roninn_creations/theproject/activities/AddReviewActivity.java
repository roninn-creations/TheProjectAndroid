package com.roninn_creations.theproject.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

    private RadioGroup ratingGroup;
    private RadioButton oneRadio;
    private RadioButton twoRadio;
    private RadioButton threeRadio;
    private RadioButton fourRadio;
    private RadioButton fiveRadio;
    private EditText commentEditor;
    private Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        place = getGson().fromJson(getIntent().getStringExtra(EXTRA_KEY_PLACE), Place.class);

        ratingGroup = findViewById(R.id.radio_group_rating);
        oneRadio = findViewById(R.id.radio_one);
        twoRadio = findViewById(R.id.radio_two);
        threeRadio = findViewById(R.id.radio_three);
        fourRadio = findViewById(R.id.radio_four);
        fiveRadio = findViewById(R.id.radio_five);
        commentEditor = findViewById(R.id.edit_comment);
        saveButton = findViewById(R.id.button_save);
        saveButton.setOnClickListener(this::onSaveButtonClick);
    }

    @Override
    public void onStop(){
        super.onStop();

        getRequestHandler().cancelRequests(TAG);
    }

    private void onSaveButtonClick(View view){
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

        Review review = new Review(
                null,
                getUser(),
                place,
                rating,
                commentEditor.getText().toString(),
                null);

        getReviewsService().create(review, this::onCreateResponse, TAG);
    }

    private void onCreateResponse(Review review) {
        finish();
    }
}
