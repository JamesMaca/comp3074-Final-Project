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
import android.widget.Filter;
import android.widget.Filterable;
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

    ArrayList<Restaurant> restaurantList = new ArrayList<>();
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
        ArrayList<Review> cafeReviewList = new ArrayList<>();
        ArrayList<Review> sushiReviewList = new ArrayList<>();

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

        // cafe sample review
        Review sample_review_1= new Review(
                "josh",
                "Nice cafe",
                4f);

        Review sample_review_2= new Review(
                "james",
                "Nice cafe",
                3f);

        Review sample_review_3= new Review(
                "vincent",
                "Nice cafe",
                3f);

        // sushi sample review
        Review sample_review_4= new Review(
                "Ritchell",
                "Delicious Sushi",
                5f);

        Review sample_review_5= new Review(
                "Stefan",
                "Affordable Sushi",
                5f);

        Review sample_review_6= new Review(
                "Pritesh",
                "Friendly Staff",
                4f);

        // add sample review to a list of reviews
        // cafe
        cafeReviewList.add(sample_review_1);
        cafeReviewList.add(sample_review_2);
        cafeReviewList.add(sample_review_3);
        // sushi
        sushiReviewList.add(sample_review_4);
        sushiReviewList.add(sample_review_5);
        sushiReviewList.add(sample_review_6);

        Restaurant sample_restaurant_1 = new Restaurant(
                1,
                "The GBCafe",
                "Cafe",
                "6415, Steeles Avenue East",
                "$$",
                cafeReviewList);

        Restaurant sample_restaurant_2 = new Restaurant(
                2,
                "GBC Sushi",
                "Sushi",
                "6415, Steeles Avenue East",
                "$",
                sushiReviewList);


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
                Intent intent = new Intent(MainActivity.this, Details.class);
//                intent.putExtra("restaurantName", restaurantList.get(position).getName());
                Restaurant selectedRestaurant = restaurantList.get(position);

                intent.putExtra("selectedRestaurant", selectedRestaurant);

                startActivity(intent);


//                Toast.makeText(getApplicationContext(),"Proceed to Details Page for " +

//                        restaurantList.get(position).getName(),Toast.LENGTH_SHORT).show();

                // Configure Details Activity Here..........


            }
        });

        // Search View Filter
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
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
            Intent intent = new Intent(MainActivity.this, AboutUs.class);
            startActivity(intent);
            //Configure About Us Activity Here.....

        }else if(item.getItemId() == R.id.adRestaurant){
            Intent intent = new Intent(MainActivity.this, add_restaurant.class);
            startActivity(intent);

        }

        return true;
    }

    private class HomeAdapter extends ArrayAdapter<Restaurant> implements Filterable {
        private Context context;
        private List<Restaurant> restaurantList;
        private List<Restaurant> filteredRestaurantList;

        public HomeAdapter(Context context, List<Restaurant> restaurantList){
            super(context,R.layout.home_row_layout,restaurantList);

            this.context = context;
            this.restaurantList = restaurantList;
            this.filteredRestaurantList = new ArrayList<>(restaurantList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView,
                            @NonNull ViewGroup parent) {
            View homeRowView = null;

            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            homeRowView = inflater.inflate(R.layout.home_row_layout,parent,false);

            Restaurant restaurant = filteredRestaurantList.get(position);

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
            ratingNumber.setText(String.valueOf(restaurant.getReview().size()));

            //Restaurant Price Estimate
            TextView priceEstimate = homeRowView.findViewById(R.id.priceEstimate);
            priceEstimate.setText(restaurant.getPriceEstimation());

            //Restaurant Type and Address
            TextView typeAndAddress = homeRowView.findViewById(R.id.typeAndAddress);

            // Forgot to add restaurant type in model
            typeAndAddress.setText( restaurant.getType() + " - "+
                    restaurant.getAddress());

            //Restaurant Status and Closing Hours
            TextView restaurantHours = homeRowView.findViewById(R.id.restaurantHours);

            // need to do some logic for when the restaurant is close or open
            restaurantHours.setText("Open" + " - " + "Closes " + "10 p.m.");

            return homeRowView;
        }

        @NonNull
        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    List<Restaurant> filteredList = new ArrayList<>();

                    if (constraint == null || constraint.length() == 0) {
                        filteredList.addAll(restaurantList);
                    } else {
                        String filterPattern = constraint.toString().toLowerCase().trim();

                        for (Restaurant restaurant : restaurantList) {
                            if (restaurant.getName().toLowerCase().contains(filterPattern)) {
                                filteredList.add(restaurant);
                            }
                        }
                    }

                    results.values = filteredList;
                    results.count = filteredList.size();
                    return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    filteredRestaurantList.clear();
                    filteredRestaurantList.addAll((List<Restaurant>) results.values);
                    notifyDataSetChanged();
                }
            };
        }
        @Override
        public int getCount() {
            return filteredRestaurantList.size();
        }

        @Nullable
        @Override
        public Restaurant getItem(int position) {
            return filteredRestaurantList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

}