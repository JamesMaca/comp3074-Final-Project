package com.georgebrown.restaurant_guide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.georgebrown.restaurant_guide.model.Address;
import com.georgebrown.restaurant_guide.model.Restaurant;
import com.georgebrown.restaurant_guide.model.Review;
import com.georgebrown.restaurant_guide.model.User;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    List<Restaurant> restaurantList = new ArrayList<>();
    ListView restaurantListView;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Sets custom toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // ========= START ========
        // Sample Data
        List<Review> reviewList = new ArrayList<>();

        // sample restaurant address
        Address sample_res_address_1 = new Address(
                160,
                "Kendal Avenue",
                "Toronto",
                "M5R 1M3");

        // sample user address
        Address sample_usr_address_1 = new Address(
                344,
                "Eglinton Avenue West",
                "Toronto",
                "M6B 1K2");

        // sample user info
        User sample_user_1 = new User(
                "John",
                "Doe",
                sample_usr_address_1,
                "4167829812");

        // sample review
        Review sample_review= new Review(
                sample_user_1,
                "Nice cafe");

        // add sample review to a list of reviews
        reviewList.add(sample_review);

        Restaurant sample_restaurant_1 = new Restaurant(
                1,
                "The GBCafe",
                "Cafe",
                sample_res_address_1,
                3.8f,
                reviewList);

        Restaurant sample_restaurant_2 = new Restaurant(
                2,
                "GBC Sushi",
                "Sushi",
                sample_res_address_1,
                5f,
                reviewList);


        // ========= END ========

        //Add restaurant into the restaurant list
        restaurantList.add(sample_restaurant_1);
        restaurantList.add(sample_restaurant_2);

        // Search View
        searchView = findViewById(R.id.searchView);

        //Listview of restaurants
        restaurantListView = findViewById(R.id.listRestaurants);

        ArrayAdapter<Restaurant> homeAdapter = new HomeAdapter(this,restaurantList);

        //displays the restaurant lists with view from home_row_layout.xml
        restaurantListView.setAdapter(homeAdapter);

        restaurantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"Proceed to Details Page for " +
                        restaurantList.get(position).getName(),Toast.LENGTH_SHORT).show();

                // Configure Details Activity Here..........


            }
        });

        // Search View Filter
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                homeAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                homeAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dropdown_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // add more if statement for other options for dropdown menu
        // configure dropdown_menu.xml first.. (under menu package in resource folder)

        if (item.getItemId() == R.id.aboutUs){
            Toast.makeText(this,"Proceed to About Us Page",Toast.LENGTH_SHORT).show();

            //Configure About Us Activity Here.....

        }else{
            //default

        }

        return true;
    }

    private class HomeAdapter extends ArrayAdapter<Restaurant>{
        private Context context;
        private List<Restaurant> restaurantList;

        public HomeAdapter(Context context, List<Restaurant> restaurantList){
            super(context,R.layout.home_row_layout,restaurantList);

            this.context = context;
            this.restaurantList = restaurantList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView,
                            @NonNull ViewGroup parent) {
            View homeRowView = null;

            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            homeRowView = inflater.inflate(R.layout.home_row_layout,parent,false);

            Restaurant restaurant = restaurantList.get(position);

            //NOTE: Still need to configure the restaurant model to store a picture

            //Restaurant name
            TextView restaurantName = homeRowView.findViewById(R.id.restaurantName);
            restaurantName.setText(restaurant.getName());

            //Restaurant Rating Star
            RatingBar ratingStar = homeRowView.findViewById(R.id.ratingBar);
            ratingStar.setRating(restaurant.getRatings());

            //Restaurant Rating Number
            TextView ratingNumber = homeRowView.findViewById(R.id.ratingNumber);
            ratingNumber.setText(String.valueOf(restaurant.getRatings()));

            //Restaurant Rating Count
            TextView ratingCount = homeRowView.findViewById(R.id.ratingCount);
            ratingNumber.setText("1"); //forgot to keep track of rating count

            //Restaurant Price Estimate
            TextView priceEstimate = homeRowView.findViewById(R.id.priceEstimate);
            priceEstimate.setText("$$"); // need to do some logic to for price estimation

            //Restaurant Type and Address
            TextView typeAndAddress = homeRowView.findViewById(R.id.typeAndAddress);

            // Forgot to add restaurant type in model
            typeAndAddress.setText( restaurant.getType() + " - "+
                    restaurant.getAddress().getLocalAddress());

            //Restaurant Status and Closing Hours
            TextView restaurantHours = homeRowView.findViewById(R.id.restaurantHours);

            // need to do some logic for when the restaurant is close or open
            restaurantHours.setText("Open" + " - " + "Closes " + "10 p.m.");

            return homeRowView;
        }
    }
}