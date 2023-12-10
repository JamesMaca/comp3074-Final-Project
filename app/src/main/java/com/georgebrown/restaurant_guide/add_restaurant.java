package com.georgebrown.restaurant_guide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.georgebrown.restaurant_guide.model.Restaurant;
import com.georgebrown.restaurant_guide.model.Review;

import java.io.Serializable;
import java.util.ArrayList;

public class add_restaurant extends AppCompatActivity {

    private EditText restName;
    private EditText restType;
    private EditText restAddress;
    private EditText mondayHours;
    private EditText tuesdayHours;
    private EditText wednesdayHours;
    private EditText thursdayHours;
    private EditText fridayHours;
    private EditText saturdayHours;
    private EditText sundayHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        restName = findViewById(R.id.rest_name_enter);
        restType = findViewById(R.id.type_enter);
        restAddress = findViewById(R.id.rest_address_enter);
        mondayHours = findViewById(R.id.monday_hours);
        tuesdayHours = findViewById(R.id.tuesday_hours);
        wednesdayHours = findViewById(R.id.wednesday_hours);
        thursdayHours = findViewById(R.id.thursday_hours);
        fridayHours = findViewById(R.id.friday_hours);
        saturdayHours = findViewById(R.id.saturday_hours);
        sundayHours = findViewById(R.id.sunday_hours);

        Button submitButton = findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = restName.getText().toString();
                String type = restType.getText().toString();
                String address = restAddress.getText().toString();
                String mondayText = mondayHours.getText().toString();
                String tuesdayText = tuesdayHours.getText().toString();
                String wednesdayText = wednesdayHours.getText().toString();
                String thursdayText = thursdayHours.getText().toString();
                String fridayText = fridayHours.getText().toString();
                String saturdayText = saturdayHours.getText().toString();
                String sundayText = sundayHours.getText().toString();


                Restaurant restaurant = new Restaurant(
                        name,
                        type,
                        address,
                        "$$",
                        new String[]{mondayText,tuesdayText,wednesdayText,thursdayText,fridayText,
                                    saturdayText,sundayText});

                Intent intent = new Intent(add_restaurant.this, MainActivity.class);
                intent.putExtra("selectedRestaurant", restaurant);
                Log.d("add_restaurant", "Selected Restaurant: " + restaurant.toString());
                startActivity(intent);

            }
        });
    }
}

