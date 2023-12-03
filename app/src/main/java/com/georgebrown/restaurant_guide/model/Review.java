package com.georgebrown.restaurant_guide.model;

public class Review {
    private User user;
    private String Review;
    private float rating;


    private float userRating;

    public Review(User user, String review, float rating) {

        this.user = user;
        this.rating = rating;
        Review = review;
        userRating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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
