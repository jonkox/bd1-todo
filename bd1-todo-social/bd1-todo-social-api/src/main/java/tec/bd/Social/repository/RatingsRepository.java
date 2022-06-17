package tec.bd.Social.repository;

import tec.bd.Social.Rating;

public interface RatingsRepository {

    Rating findById(int id);

    float findAverage(String todoId);

    boolean deleteRating(String todoId, String clientId);

    Rating newRating(Rating newRating);
}
