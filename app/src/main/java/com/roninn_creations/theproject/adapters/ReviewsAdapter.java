package com.roninn_creations.theproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.roninn_creations.theproject.R;
import com.roninn_creations.theproject.models.Review;

import java.util.List;

public class ReviewsAdapter extends ArrayAdapter<Review> {

    private Context context;

    public ReviewsAdapter(Context context, List<Review> reviews) {
        super(context, 0, reviews);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Review review = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_review, parent, false);
        }
        TextView userText = convertView.findViewById(R.id.user_text);
        TextView ratingText = convertView.findViewById(R.id.rating_text);
        TextView commentText = convertView.findViewById(R.id.comment_text);
        if (review != null){
            userText.setText(review.getUser().getName());
            ratingText.setText(Integer.toString(review.getRating()));
            commentText.setText(review.getComment());
        }
        return convertView;
    }
}
