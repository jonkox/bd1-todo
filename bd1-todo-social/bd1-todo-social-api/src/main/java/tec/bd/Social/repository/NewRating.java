package tec.bd.Social.repository;

import tec.bd.Social.Rating;

public class NewRating {

    public String createdAt;
    public String rating;

    public NewRating(){}

    public NewRating(String createdAt, String rating) {
        this.createdAt = createdAt;
        this.rating = rating;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
