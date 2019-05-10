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

import static com.roninn_creations.theproject.TheProjectApplication.getPlacesService;
import static com.roninn_creations.theproject.TheProjectApplication.getRequestHandler;

public class AddPlaceActivity extends AppCompatActivity {

    private static final String TAG = AddPlaceActivity.class.getName();

    private EditText nameEditor;
//    private EditText addressEditor;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        nameEditor = findViewById(R.id.edit_name);
//        addressEditor = findViewById(R.id.edit_address);
        saveButton = findViewById(R.id.button_save);
        saveButton.setOnClickListener(this::onSaveButtonClick);
    }

    @Override
    public void onStop(){
        super.onStop();

        getRequestHandler().cancelRequests(TAG);
    }

    private void onSaveButtonClick(View view){
//        String category;
//        if (barRadio.isChecked())
//            category = Place.Category.BAR.toString();
//        else if (cafeRadio.isChecked())
//            category = Place.Category.CAFE.toString();
//        else if (hotelRadio.isChecked())
//            category = Place.Category.HOTEL.toString();
//        else if (restaurantRadio.isChecked())
//            category = Place.Category.RESTAURANT.toString();
//        else
//            category = Place.Category.NA.toString();
//
//        Place place = new Place(
//                null,
//                nameEditor.getText().toString(),
//                addressEditor.getText().toString(),
//                category);
//
//        getPlacesService().create(place, this::onCreateResponse, TAG);
    }

    private void onCreateResponse(Place place){
        finish();
    }
}
