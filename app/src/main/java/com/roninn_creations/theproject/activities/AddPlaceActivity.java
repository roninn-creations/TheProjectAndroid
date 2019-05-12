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
import android.widget.TextView;

import com.roninn_creations.theproject.R;
import com.roninn_creations.theproject.models.Place;

import static com.roninn_creations.theproject.TheProjectApplication.getGson;
import static com.roninn_creations.theproject.TheProjectApplication.getPlacesService;
import static com.roninn_creations.theproject.TheProjectApplication.getRequestHandler;

public class AddPlaceActivity extends AppCompatActivity {

    private static final String TAG = AddPlaceActivity.class.getName();

    private ProgressBar progressBar;
    private EditText nameEdit;
    private EditText streetEdit;
    private EditText postEdit;
    private EditText cityEdit;
    private EditText tagsEdit;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        progressBar = findViewById(R.id.progress_bar);
        nameEdit = findViewById(R.id.edit_name);
        streetEdit = findViewById(R.id.edit_street);
        postEdit = findViewById(R.id.edit_post);
        cityEdit = findViewById(R.id.edit_city);
        tagsEdit = findViewById(R.id.edit_tags);
        saveButton = findViewById(R.id.button_save);

        tagsEdit.setOnEditorActionListener(this::onEditorSend);
        saveButton.setOnClickListener(this::onSaveButtonClick);
    }

    @Override
    public void onStop(){
        super.onStop();
        getRequestHandler().cancelRequests(TAG);
    }

    private void onSaveButtonClick(View view){
        submitPlace();
    }

    private boolean onEditorSend(TextView view, int actionId, KeyEvent event){
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            submitPlace();
            handled = true;
        }
        return handled;
    }

    private void submitPlace(){
        String name = nameEdit.getText().toString();
        String street = streetEdit.getText().toString();
        String post = postEdit.getText().toString();
        String city = cityEdit.getText().toString();
        String[] tags = tagsEdit.getText().toString().split("[\\s.,]+");
        Place place = new Place(null, name, street, post, city, tags);
        getPlacesService().create(place,
                this::onCreateResponse, this::onErrorResponse, TAG);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void onCreateResponse(Place place){
        Intent placeIntent = new Intent(this, PlaceActivity.class);
        placeIntent.putExtra(PlaceActivity.EXTRA_KEY_PLACE, getGson().toJson(place));
        startActivity(placeIntent);
    }

    private void onErrorResponse(String message){
        progressBar.setVisibility(View.GONE);
        Snackbar.make(saveButton, message, Snackbar.LENGTH_LONG).show();
    }
}
