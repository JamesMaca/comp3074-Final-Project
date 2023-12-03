package com.georgebrown.restaurant_guide.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class Restaurant implements Serializable {

    //TO CHANGE:
        // auto gen id
        // refctore to arraylist
        //hours of opperation for each day of the week
        // address to a string instead of object
    private int _ID;
    private String name;
    private String type;
    private String Address;
    private float ratings; //avg rating
    private String priceEstimation;
    private ArrayList<Review> reviewList;

    public Restaurant(int _ID,
                      String name,
                      String type,
                      com.georgebrown.restaurant_guide.model.Address address,
                      String priceEstimation,
                      List<Review> reviewList) {
        this._ID = _ID;
        this.type = type;
        this.name = name;
        this.Address = address;
        this.priceEstimation = priceEstimation;
        this.reviewList = reviewList;
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

    public com.georgebrown.restaurant_guide.model.Address getAddress() {
        return Address;
    }

    public void setAddress(com.georgebrown.restaurant_guide.model.Address address) {
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

    public List<Review> getReview() {
        return reviewList;
    }

    public void setReview(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    private float setAverageRating() {
        float resAvgRating = 0;
        for (Review review : reviewList){
            resAvgRating += review.getRating();
        }
        return resAvgRating/reviewList.size();
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "_ID=" + _ID +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", Address=" + Address +
                ", ratings=" + ratings +
                ", priceEstimation='" + priceEstimation + '\'' +
                ", reviewList=" + reviewList +
                '}';
    }
}
