package com.roninn_creations.theproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.roninn_creations.theproject.R;
import com.roninn_creations.theproject.models.Place;

import java.util.Arrays;
import java.util.List;

public class PlacesAdapter extends ArrayAdapter<Place> {

    private Context context;

    public PlacesAdapter(Context context, List<Place> places) {
        super(context, 0, places);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Place place = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_place, parent, false);
        }
        TextView nameText = convertView.findViewById(R.id.text_title);
        TextView addressText = convertView.findViewById(R.id.text_address);
        TextView tagsText = convertView.findViewById(R.id.text_tags);
        if (place != null){
            nameText.setText(place.getName());
            addressText.setText(place.getAddress().toString());
            tagsText.setText(Arrays.toString(place.getTags()));
        }
        return convertView;
    }
}
