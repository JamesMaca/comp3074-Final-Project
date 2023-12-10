package com.georgebrown.restaurant_guide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.georgebrown.restaurant_guide.model.Restaurant;

public class edit_restaurant extends AppCompatActivity {

    public Restaurant editRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);

        EditText editRestaurantName = findViewById(R.id.rest_name_enter);

        Intent intent = getIntent();
        if(intent != null){
            editRestaurant = (Restaurant) intent.getSerializableExtra("editRestaurant");
            editRestaurantName.setText(editRestaurant.getName());



        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        Button submitEdit = findViewById(R.id.submit);

        submitEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editRestaurant != null) {
                    String editedName = editRestaurantName.getText().toString();
                    editRestaurant.setName(editedName);

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("editedRestaurant", editRestaurant); // Use the correct key
                    Log.d("EditRestaurant", "Edited Name: " + editedName);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });

    }
}