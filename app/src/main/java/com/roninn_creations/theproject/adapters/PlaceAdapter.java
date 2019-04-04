package com.roninn_creations.theproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.roninn_creations.theproject.models.Place;

import java.util.ArrayList;

public class PlaceAdapter extends ArrayAdapter<Place> {

    private Context context;

    public PlaceAdapter(Context context, ArrayList<Place> places) {
        super(context, 0, places);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Place place = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(null, parent, false);
        }
        return convertView;
    }
}
