package com.georgebrown.restaurant_guide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
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


        String[] dailyHours = {
                "monday_hours",
                "tuesday_hours",
                "wednesday_hours",
                "thursday_hours",
                "friday_hours",
                "saturday_hours",
                "sunday_hours"};

        Restaurant new_restaurant = new Restaurant(
                "The GBCafe",
                "Cafe",
                "160 Kendall Avenue",
                "$$",
                new ArrayList<>(),
                dailyHours);

        restaurantList.add(new_restaurant);

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

        Intent intent = getIntent();
        if (intent != null) {
            Restaurant selectedRestaurant = (Restaurant) intent.getSerializableExtra("selectedRestaurant");
            if (selectedRestaurant != null) {
                // Log statement to check the state of selectedRestaurant
                Log.d("MainActivity", "Selected Restaurant: " + selectedRestaurant.getName());
                // Continue with your logic if needed
            }
        }

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

    public class HomeAdapter extends ArrayAdapter<Restaurant> {

        private Context context;
        private List<Restaurant> restaurantList;
        private List<Restaurant> filteredRestaurantList;

        public HomeAdapter(@NonNull Context context, @NonNull List<Restaurant> restaurantList) {
            super(context, R.layout.home_row_layout, restaurantList);

            this.context = context;
            this.restaurantList = restaurantList;
            this.filteredRestaurantList = new ArrayList<>(restaurantList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View homeRowView = null;

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            homeRowView = inflater.inflate(R.layout.home_row_layout, parent, false);

            Restaurant restaurant = filteredRestaurantList.get(position);

            // Restaurant Image
            //ImageView restaurantImage = homeRowView.findViewById(R.id.restaurantImage);
            //TODO: Configure image loading from a URL

            // Restaurant name
            TextView restaurantName = homeRowView.findViewById(R.id.restaurantName);
            restaurantName.setText(restaurant.getName());

            // Restaurant Rating Star
            RatingBar ratingStar = homeRowView.findViewById(R.id.ratingBar);
            ratingStar.setRating(restaurant.getRatings());

            // Restaurant Rating Number
            TextView ratingNumber = homeRowView.findViewById(R.id.ratingNumber);
            ratingNumber.setText(String.valueOf(restaurant.getRatings()));

            // Restaurant Rating Count
            TextView ratingCount = homeRowView.findViewById(R.id.ratingCount);
            ratingCount.setText(String.valueOf(restaurant.getReviewList().size()));


            // Restaurant Price Estimate
            TextView priceEstimate = homeRowView.findViewById(R.id.priceEstimate);
            priceEstimate.setText(restaurant.getPriceEstimation());

            // Restaurant Type and Address
            TextView typeAndAddress = homeRowView.findViewById(R.id.typeAndAddress);
            typeAndAddress.setText(restaurant.getType() + " - " + restaurant.getAddress());

            // Restaurant Status and Closing Hours
            TextView restaurantHours = homeRowView.findViewById(R.id.restaurantHours);
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