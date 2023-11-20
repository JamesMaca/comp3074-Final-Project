package com.georgebrown.restaurant_guide.model;

import java.util.List;

public class Restaurant {

    private int _ID;
    private String name;
    private String type;
    private Address Address;
    private float ratings;
    private List<Review> review;

    public Restaurant(int _ID, String name,String type,
                      com.georgebrown.restaurant_guide.model.Address address,
                      float ratings, List<Review> review) {
        this._ID = _ID;
        this.type = type;
        this.name = name;
        Address = address;
        this.ratings = ratings;
        this.review = review;
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

    public float getRatings() {
        return ratings;
    }

    public void setRatings(float ratings) {
        this.ratings = ratings;
    }

    public List<Review> getReview() {
        return review;
    }

    public void setReview(List<Review> review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "_ID=" + _ID +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", Address=" + Address +
                ", ratings=" + ratings +
                ", review=" + review +
                '}';
    }
}
