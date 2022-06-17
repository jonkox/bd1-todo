package tec.bd.Social.repository;

import tec.bd.Social.Review;

import java.util.List;

public interface ReviewsRepository {

    List<Review> getReview(String todoId);

    Review newReview(Review review);

    boolean delReview(String todoId, String clientId);

    Review updateReview(String todoId, String clientid);

}
