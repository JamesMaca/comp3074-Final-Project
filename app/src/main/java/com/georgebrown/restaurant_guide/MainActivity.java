package com.georgebrown.restaurant_guide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.georgebrown.restaurant_guide.model.Restaurant;
import com.georgebrown.restaurant_guide.model.Review;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_RESTAURANT_REQUEST = 1;
    static ArrayList<Restaurant> restaurantList = new ArrayList<>();
    static boolean initialDataLoaded = false;
    ListView restaurantListView;
    SearchView searchView;
    HomeAdapter homeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (!initialDataLoaded){
            String[] hoursOfOperation = {
                    "8AM - 10PM",
                    "8AM - 10PM",
                    "8AM - 10PM",
                    "8AM - 10PM",
                    "8AM - 10PM",
                    "10AM - 6PM",
                    "CLOSED"};

            // cafe sample review
            Review sample_review_1 = new Review(
                    "Eleanor Quinn",
                    "Nice cafe",
                    4f);

            Review sample_review_2 = new Review(
                    "Miles Rodriguez",
                    "Nice cafe",
                    3f);

            Review sample_review_3 = new Review(
                    "Aurora Chang",
                    "Nice cafe",
                    3f);

            // sushi sample review
            Review sample_review_4 = new Review(
                    "Caleb Harper",
                    "Delicious Sushi",
                    5f);

            Review sample_review_5 = new Review(
                    "Isabella Jensen",
                    "Affordable Sushi",
                    5f);

            Review sample_review_6 = new Review(
                    "Malik Thompson",
                    "Friendly Staff",
                    4f);

            Restaurant The_GBCafe = new Restaurant(
                    "The GBCafe",
                    "Cafe",
                    "6415, Steeles Avenue East, Toronto",
                    "$$",
                    hoursOfOperation);

            Restaurant GBC_Sushi = new Restaurant(
                    "GBC Sushi",
                    "Sushi",
                    "111 King Street West, Toronto",
                    "$$",
                    hoursOfOperation);

            // GBCafe
            The_GBCafe.addReview(sample_review_1);
            The_GBCafe.addReview(sample_review_2);
            The_GBCafe.addReview(sample_review_3);

            // GBC SUSHI
            GBC_Sushi.addReview(sample_review_4);
            GBC_Sushi.addReview(sample_review_5);
            GBC_Sushi.addReview(sample_review_6);

            restaurantList.add(The_GBCafe);
            restaurantList.add(GBC_Sushi);

            initialDataLoaded = true;
        }


        // Search View
        searchView = findViewById(R.id.searchView);

        // Listview of restaurants
        restaurantListView = findViewById(R.id.listRestaurants);

        homeAdapter = new HomeAdapter(this, restaurantList);

        // Displays the restaurant lists with view from home_row_layout.xml
        restaurantListView.setAdapter(homeAdapter);


        Restaurant newRestaurant = (Restaurant) getIntent().getSerializableExtra("selectedRestaurant");

        if (newRestaurant != null) {

            Log.d("MainActivity", "Received Restaurant: " + newRestaurant.toString());

            homeAdapter.addNewRestaurant(newRestaurant);

        } else {
            Log.e("MainActivity", "Received null restaurant");
        }

        restaurantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant selectedRestaurant = homeAdapter.getItem(position);

                if (selectedRestaurant != null) {
                    Intent detailsIntent = new Intent(MainActivity.this, Details.class);
                    detailsIntent.putExtra("selectedRestaurant", selectedRestaurant);
                    startActivityForResult(detailsIntent, 1);
                } else {
                    Log.e("MainActivity", "Selected restaurant is null");
                }
            }
        });

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

        if (item.getItemId() == R.id.aboutUs) {
            Intent intent = new Intent(MainActivity.this, AboutUs.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.adRestaurant) {
            Intent intent = new Intent(MainActivity.this, add_restaurant.class);
            startActivityForResult(intent, ADD_RESTAURANT_REQUEST);
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                Restaurant updatedRestaurant = (Restaurant) data.getSerializableExtra("editedRestaurant");
                if (updatedRestaurant != null) {
                    boolean restaurantExists = false;
                    for (int i = 0; i < restaurantList.size(); i++) {
                        if (restaurantList.get(i).getName().equals(updatedRestaurant.getName())) {
                            restaurantList.set(i, updatedRestaurant);
                            restaurantExists = true;
                            break;
                        }
                    }

                    if (!restaurantExists) {
                        restaurantList.add(updatedRestaurant);
                    }

                    homeAdapter.notifyDataSetChanged();
                }
            }
        }
    }


    private class HomeAdapter extends ArrayAdapter<Restaurant> implements Filterable {
        private Context context;
        private List<Restaurant> restaurantList;
        private List<Restaurant> filteredRestaurantList;

        public void addNewRestaurant(Restaurant newRestaurant) {

            restaurantList.add(newRestaurant);
            filteredRestaurantList.add(newRestaurant);

            notifyDataSetChanged();

            Log.d("HomeAdapter", "Restaurants Received: " + restaurantList.toString());
        }

        public HomeAdapter(Context context, List<Restaurant> restaurantList) {
            super(context, R.layout.home_row_layout, restaurantList);

            this.context = context;
            this.restaurantList = restaurantList;
            this.filteredRestaurantList = new ArrayList<>(restaurantList);
            Log.d("HomeAdapter", "Restaurant List Contents: " + restaurantList.toString());
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView,
                            @NonNull ViewGroup parent) {

            View homeRowView = null;

            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            homeRowView = inflater.inflate(R.layout.home_row_layout, parent, false);

            Log.d("MainActivity", "Filtered Restaurant List Contents: " + homeAdapter.filteredRestaurantList.toString());

            Restaurant restaurant = filteredRestaurantList.get(position);

            // Get the current time
            Calendar calendar = Calendar.getInstance();

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

            //Restaurant Type and Address
            TextView typeAndAddress = homeRowView.findViewById(R.id.typeAndAddress);
            typeAndAddress.setText(restaurant.getType() + " - " +
                    restaurant.getAddress());

            //Restaurant Status and Closing Hours
            TextView restaurantHours = homeRowView.findViewById(R.id.restaurantHours);

            // need to do some logic for when the restaurant is close or open
            restaurantHours.setText(getHoursOfOperationToday(restaurant, calendar.get(Calendar.DAY_OF_WEEK)));

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

        private String getHoursOfOperationToday(Restaurant restaurant, int dayOfWeek) {
            switch (dayOfWeek) {
                case Calendar.MONDAY:
                    return restaurant.getHoursOfOperation()[0];
                case Calendar.TUESDAY:
                    return restaurant.getHoursOfOperation()[1];
                case Calendar.WEDNESDAY:
                    return restaurant.getHoursOfOperation()[2];
                case Calendar.THURSDAY:
                    return restaurant.getHoursOfOperation()[3];
                case Calendar.FRIDAY:
                    return restaurant.getHoursOfOperation()[4];
                case Calendar.SATURDAY:
                    return restaurant.getHoursOfOperation()[5];
                case Calendar.SUNDAY:
                    return restaurant.getHoursOfOperation()[6];
            }
            return "Not Available";
        }
    }
}