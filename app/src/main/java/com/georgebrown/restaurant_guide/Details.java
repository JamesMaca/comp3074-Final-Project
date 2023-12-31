package com.georgebrown.restaurant_guide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.georgebrown.restaurant_guide.model.Restaurant;
import com.georgebrown.restaurant_guide.model.Review;

import java.util.ArrayList;

public class Details extends AppCompatActivity {
    private Restaurant selectedRestaurant;
    private static final int EDIT_RESTAURANT_REQUEST = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);


        Intent intent = getIntent();
        if(intent != null){
            selectedRestaurant = (Restaurant) intent.getSerializableExtra("selectedRestaurant");
            if (selectedRestaurant != null) {
                updateUIWithRestaurantDetails();
            } else {
                Log.e("DetailsActivity", "selectedRestaurant is null");
            }


            updateUIWithRestaurantDetails();

            //Name + Review
            TextView restaurant_name = findViewById(R.id.restaurante_name);
            TextView restaurant_avg_rating = findViewById(R.id.avg_rating);

            String avg_review = String.valueOf(selectedRestaurant.getRatings() + "/5.0");

            restaurant_avg_rating.setText(avg_review);
            restaurant_name.setText(selectedRestaurant.getName());


            TextView restaurant_address = findViewById(R.id.address);
            restaurant_address.setText(selectedRestaurant.getAddress());

            //Hours or operation
            String[] hoursOfOperation = selectedRestaurant.getHoursOfOperation();
            TextView sunday = findViewById(R.id.sunday);
            TextView monday = findViewById(R.id.monday);
            TextView tuesday = findViewById(R.id.tuesday);
            TextView wednesday = findViewById(R.id.wednesday);
            TextView thursday = findViewById(R.id.thursday);
            TextView friday = findViewById(R.id.friday);
            TextView saturday = findViewById(R.id.saturday);

            sunday.setText("Sunday - " + hoursOfOperation[0]);
            monday.setText("Monday - " + hoursOfOperation[1]);
            tuesday.setText("Tuesday - " + hoursOfOperation[2]);
            wednesday.setText("Wednesday - " + hoursOfOperation[3]);
            thursday.setText("Thursday - " + hoursOfOperation[4]);
            friday.setText("Friday - " + hoursOfOperation[5]);
            saturday.setText("Saturday - " + hoursOfOperation[6]);


            //Review1
            TextView review1_username = findViewById(R.id.review1_username);
            TextView review1_rating = findViewById(R.id.review1_rating);
            TextView review1_review = findViewById(R.id.review1_review);

            if (!selectedRestaurant.getReviewList().isEmpty()) {
                Review firstReview = selectedRestaurant.getReviewList().get(0);
                String reviewUser1 = firstReview.getUser();
                Float reviewRating1 = firstReview.getRating();
                String reviewComment1 = firstReview.getReview();

                review1_username.setText(reviewUser1 + " ");
                review1_rating.setText(String.valueOf(reviewRating1) + "/5.0");
                review1_review.setText(reviewComment1);
            } else {
                review1_username.setText("No reviews available");
                review1_rating.setText("");
                review1_review.setText("");
            }

            //Review2
            TextView review2_username = findViewById(R.id.review2_username);
            TextView review2_rating = findViewById(R.id.review2_rating);
            TextView review2_review = findViewById(R.id.review2_review);


            if (!selectedRestaurant.getReviewList().isEmpty()) {
                Review lastReview = selectedRestaurant.getReviewList().get(selectedRestaurant.getReviewList().size() - 1);
                String reviewUser2 = lastReview.getUser();
                Float reviewRating2 = lastReview.getRating();
                String reviewComment2 = lastReview.getReview();

                review2_username.setText(reviewUser2 + " ");
                review2_rating.setText(String.valueOf(reviewRating2) + "/5.0");
                review2_review.setText(reviewComment2);
            } else {
                review2_username.setText("");
                review2_rating.setText("");
                review2_review.setText("");
            }

                restaurant_name.setText(selectedRestaurant.getName());
        }


        //Navbar ----------------------------------------------------------------------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent(Details.this,MainActivity.class);
                resultIntent.putExtra("selectedRestaurant", selectedRestaurant);
                setResult(RESULT_OK, resultIntent);
                startActivity(resultIntent);
                finish();
            }
        });

        // Edit Restaurant Details ------------------------------------------------------
        Button btnEdit = findViewById(R.id.edit_listing);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(Details.this, edit_restaurant.class);
                editIntent.putExtra("editRestaurant", selectedRestaurant);
                startActivityForResult(editIntent, EDIT_RESTAURANT_REQUEST);
            }
        });


        // Make Review --------------------------------------------------------------
        ImageButton makeReview = findViewById(R.id.add_review_btn);

        makeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                if(intent != null) {
                    Restaurant selectedRestaurant = (Restaurant) intent.getSerializableExtra("selectedRestaurant");
                    Intent reviewIntent = new Intent(Details.this, ReviewInterface.class);


                    reviewIntent.putExtra("selectedRestaurant", selectedRestaurant);

                    startActivity(reviewIntent);
                }
            }
        });

        //Google maps function------------------------------
        ImageButton viewMap = findViewById(R.id.map_btn);
        TextView restaurant_address = findViewById(R.id.address);

        viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = restaurant_address.getText().toString();

                Uri uri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
                Intent intent = new Intent(Intent. ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        // Share via email---------------------------------
        ImageButton shareBtn = findViewById(R.id.share_btn);
        TextView restaurant_name = findViewById(R.id.restaurante_name);

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = restaurant_address.getText().toString();
                String name = restaurant_name.getText().toString();
                String restaurant_info = "Restaurant Name: " + name + "\nAddress: " + address;

                Intent intentEmail = new Intent(Intent.ACTION_SENDTO);
                intentEmail.setData(Uri.parse("mailto:"));
                intentEmail.putExtra(Intent.EXTRA_SUBJECT, restaurant_name + " info!");
                intentEmail.putExtra(Intent.EXTRA_TEXT, restaurant_info);

                startActivity(intentEmail);

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_RESTAURANT_REQUEST && resultCode == RESULT_OK && data != null) {

            Restaurant editedRestaurant = (Restaurant) data.getSerializableExtra("editedRestaurant");

            if (editedRestaurant != null) {

                selectedRestaurant = editedRestaurant;

                updateUIWithRestaurantDetails();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("editedRestaurant", editedRestaurant);
                setResult(RESULT_OK, resultIntent);
            }
        }
    }

    private void updateUIWithRestaurantDetails() {
        // Update the UI with the edited restaurant details
        Log.d("DetailsActivity", "Updating UI with edited details");

        if (selectedRestaurant != null) {
            Log.d("DetailsActivity", "Selected Restaurant is not null");
            // Name + Review
            TextView restaurant_name = findViewById(R.id.restaurante_name);

            Log.d("DetailsActivity", "Selected Restaurant Name: " + selectedRestaurant.getName());

            String avg_review = String.valueOf(selectedRestaurant.getRatings() + "/5.0");

            restaurant_name.setText(selectedRestaurant.getName());
        }


    }

    private void updateSelectedRestaurant(Restaurant updatedRestaurant) {
        for (int i = 0; i < MainActivity.restaurantList.size(); i++) {
            if (MainActivity.restaurantList.get(i).getName().equals(updatedRestaurant.getName())) {
                MainActivity.restaurantList.set(i, updatedRestaurant);
                break;
            }
        }
    }

}
