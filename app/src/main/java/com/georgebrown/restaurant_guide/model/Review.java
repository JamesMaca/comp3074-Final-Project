package com.georgebrown.restaurant_guide.model;

public class Review {
    private User user;
    private String Review;

    public Review(User user, String review) {
        this.user = user;
        Review = review;
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

    @Override
    public String toString() {
        return "Review{" +
                "user=" + user +
                ", Review='" + Review + '\'' +
                '}';
    }
}
