package tec.bd.Social.service;

import tec.bd.Social.Rating;
import tec.bd.Social.repository.RatingsRepository;

public class RatingsServiceImpl implements RatingsService{

    private RatingsRepository ratingsRepository;


    public RatingsServiceImpl(RatingsRepository ratingsRepository){
        this.ratingsRepository = ratingsRepository;
    }


    @Override
    public Rating newRating(Rating newRating) {
        return this.ratingsRepository.newRating(newRating);
    }

    @Override
    public Rating getRating(int ratingId) {
        return this.ratingsRepository.findById(ratingId);
    }

    @Override
    public float getRatingAverage(String todoId) {
        return this.ratingsRepository.findAverage(todoId);
    }

    @Override
    public boolean deleteRating(String todoId, String clientId) {
        return this.ratingsRepository.deleteRating(todoId, clientId);
    }
}
