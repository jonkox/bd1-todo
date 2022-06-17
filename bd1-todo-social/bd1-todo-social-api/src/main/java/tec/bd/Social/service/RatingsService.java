package tec.bd.Social.service;

import tec.bd.Social.Rating;

public interface RatingsService {


    Rating newRating(Rating newRating);

    Rating getRating(int ratingId);

    float getRatingAverage(String todoId);

    boolean deleteRating(String  todoId, String clientId);

}
