package com.georgebrown.restaurant_guide.model;

import java.io.Serializable;

public class Review implements Serializable {

    //TO CHANGE
        // change user to string
        //
    private String user;
    private String Review;
    private float rating;

    private float userRating;

    public Review(String user, String review, float rating) {

        this.user = user;
        this.rating = rating;
        Review = review;
        userRating = rating;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getReview() {
        return Review;
    }

    public void setReview(String review) {
        Review = review;
    }
  
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) { 
      this.rating = rating; 
    }

    @Override
    public String toString() {
        return "Review{" +
                "user=" + user +
                ", Review='" + Review + '\'' +
                ", rating=" + rating +
                '}';
    }
}
