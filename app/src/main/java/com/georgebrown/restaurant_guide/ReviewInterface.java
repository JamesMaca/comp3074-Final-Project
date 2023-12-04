package com.georgebrown.restaurant_guide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.georgebrown.restaurant_guide.model.Review;
import com.georgebrown.restaurant_guide.model.User;

import java.util.ArrayList;

public class ReviewInterface extends AppCompatActivity {

    private ArrayList<Review> reviewList = new ArrayList<>();

    public ArrayList<Review> getReviewList() {
        return reviewList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_interface);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);

        TextView ratingPlaceholder = findViewById(R.id.ratingReview);

        RatingBar ratingBar = findViewById(R.id.ratingBarReview);

        EditText reviewEditText = findViewById(R.id.reviewEditText);

        Button submitButton = findViewById(R.id.submitReview);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Details.class));
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reviewText = reviewEditText.getText().toString();
                float rating = ratingBar.getRating();

                if (!reviewText.isEmpty()) {
                    String user = "John";
                    Review newReview = new Review(user, reviewText, rating);
                    reviewList.add(newReview);
                    showToast("Review added: " + reviewText + ", rating: " + rating);
                } else {
                    showToast("Please write a valid review or make sure review isn't empty.");
                }
            }
        });

    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
