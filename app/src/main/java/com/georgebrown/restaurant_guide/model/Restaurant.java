package com.georgebrown.restaurant_guide.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Restaurant implements Serializable {

    //TO CHANGE:
        // auto gen id
        // refctore to arraylist
        //hours of opperation for each day of the week
        // address to a string instead of object
    private static int restaurantCount = 0;
    private int _ID;
    private String name;
    private String type;
    private String Address;
    private float ratings; //avg rating
    private String priceEstimation;
    private ArrayList<Review> reviewList;
    private String[] dailyHours;


    public Restaurant(String name,
                      String type,
                      String address,
                      String priceEstimation,
                      ArrayList<Review> reviewList,
                      String[] dailyHours) {
        restaurantCount++;
        this._ID = restaurantCount;
        this.type = type;
        this.name = name;
        this.Address = address;
        this.priceEstimation = priceEstimation;
        this.reviewList = reviewList;
        this.dailyHours = dailyHours;
        ratings = setAverageRating();
    }

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int ID) {
        this._ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPriceEstimation() {
        return priceEstimation;
    }

    public void setPriceEstimation(String priceEstimation) {
        this.priceEstimation = priceEstimation;
    }

    public float getRatings() {
        return ratings;
    }

    public void setRatings(float ratings) { this.ratings = ratings; }

    public ArrayList<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(ArrayList<Review> reviewList) {
        this.reviewList = reviewList;
    }

    private float setAverageRating() {
        float resAvgRating = 0;
        for (Review review : reviewList){
            resAvgRating += review.getRating();
        }
        return resAvgRating/reviewList.size();
    }

    public String[] getDailyHours() {
        return dailyHours;
    }

    public void setDailyHours(String[] dailyHours) {
        this.dailyHours = dailyHours;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "_ID=" + _ID +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", Address='" + Address + '\'' +
                ", ratings=" + ratings +
                ", priceEstimation='" + priceEstimation + '\'' +
                ", reviewList=" + reviewList +
                ", dailyHours=" + Arrays.toString(dailyHours) +
                '}';
    }
}
