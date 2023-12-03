package com.georgebrown.restaurant_guide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.georgebrown.restaurant_guide.model.Restaurant;

public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        //Dynamically displaying restaurant details ---------------------------------------------
        Intent intent = getIntent();
        if(intent != null){
            Restaurant selectedRestaurant = (Restaurant) intent.getSerializableExtra("selectedRestaurant");
            //            String restaurantName = intent.getStringExtra("restaurantName");
////            Restaurant selectedRestaurant = (Restaurant) intent.getSerializableExtra("restaurant");
            TextView restaurant_name = findViewById(R.id.restaurante_name);
////            restaurant_name.setText(selectedRestaurant.getName());
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
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        Button btnEdit = findViewById(R.id.edit_listing);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Details.this, edit_restaurant.class);
                startActivity(intent);
            }
        });


        ImageButton makeReview = findViewById(R.id.add_review_btn);

        makeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Details.this, ReviewInterface.class);
                startActivity(intent);
            }
        });

        //Google maps function------------------------------------------------------------------------

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

    }


}
