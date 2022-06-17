package tec.bd.Social.service;

import tec.bd.Social.Review;
import tec.bd.Social.repository.ReviewsRepository;

import java.util.List;

public class ReviewsServicesImpl implements ReviewsServices{

    private ReviewsRepository reviewsRepository;

    public ReviewsServicesImpl(ReviewsRepository reviewsRepository) {
        this.reviewsRepository = reviewsRepository;
    }

    @Override
    public List<Review> getReview(String todoId) {
        return this.reviewsRepository.getReview(todoId);
    }

    @Override
    public Review newReview(Review review) {
        return this.reviewsRepository.newReview(review);
    }

    @Override
    public boolean delReview(String todoId, String clientId) {
        return this.reviewsRepository.delReview(todoId,clientId);
    }

    @Override
    public Review updateReview(Review review) {
        return this.reviewsRepository.updateReview(review);
    }
}
