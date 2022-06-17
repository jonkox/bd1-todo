package tec.bd.Social.service;

import tec.bd.Social.Review;

import java.util.List;

public interface ReviewsServices {

    List<Review> getReview(String todoId);

    Review newReview(Review review);

    boolean delReview(String todoId, String clientId);

    Review updateReview(Review review);


}
