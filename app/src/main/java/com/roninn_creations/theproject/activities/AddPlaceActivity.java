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
import android.widget.RadioGroup;
import android.widget.TextView;

import com.roninn_creations.theproject.R;
import com.roninn_creations.theproject.models.Place;

import static com.roninn_creations.theproject.TheProjectApplication.getGson;
import static com.roninn_creations.theproject.TheProjectApplication.getPlacesService;
import static com.roninn_creations.theproject.TheProjectApplication.getRequestHandler;

public class AddPlaceActivity extends AppCompatActivity {

    private static final String TAG = AddPlaceActivity.class.getName();

    private EditText nameEditor;
    private EditText streetEditor;
    private EditText postEditor;
    private EditText cityEditor;
    private EditText tagsEditor;
    private ProgressBar progressBar;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        nameEditor = findViewById(R.id.edit_name);
        streetEditor = findViewById(R.id.edit_street);
        postEditor = findViewById(R.id.edit_post);
        cityEditor = findViewById(R.id.edit_city);
        tagsEditor = findViewById(R.id.edit_tags);
        tagsEditor.setOnEditorActionListener(this::onEditorSend);
        progressBar = findViewById(R.id.progress_bar);
        saveButton = findViewById(R.id.button_save);
        saveButton.setOnClickListener(this::onSaveButtonClick);
    }

    @Override
    protected void onStart(){
        super.onStart();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStop(){
        super.onStop();
        getRequestHandler().cancelRequests(TAG);
    }

    private void onSaveButtonClick(View view){
        submit();
    }

    private boolean onEditorSend(TextView v, int actionId, KeyEvent event){
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            submit();
            handled = true;
        }
        return handled;
    }

    private void submit(){
        String name = nameEditor.getText().toString();
        String street = streetEditor.getText().toString();
        String post = postEditor.getText().toString();
        String city = cityEditor.getText().toString();
        String[] tags = tagsEditor.getText().toString().split("[\\s.,]+");
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
