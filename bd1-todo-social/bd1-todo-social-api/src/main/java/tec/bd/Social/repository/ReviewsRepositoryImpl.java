package tec.bd.Social.repository;

import tec.bd.Social.Review;
import tec.bd.Social.datasource.DBManager;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewsRepositoryImpl extends BaseRepository<Review> implements ReviewsRepository{

    private static final String FIND_ALL_Reviews = "select id, todoid, createdat, comentario, clientid from review where todoid = ?;";
    private static final String FIND_BY_RATING_ID_QUERY = "select id,todoid,ratingvalue,createdat,clientid from rating where id = ?";
    private static final String CALCULATE_AVG_PROC = "{CALL calculate_average_rating(?)}";
    private static final String DELETE_REVIEW = "delete from review where todoid = ? and clientid = ?;";
    private static final String NEW_RATING = "{CALL insert_rating(?,?,?,?);}";
    private static final String UPDATE = "update review set title = ?, description = ?, state = ?, startdate = ?, enddate = ? where tid = ?";



    public ReviewsRepositoryImpl(DBManager dbManager) {
        super(dbManager);
    }

    @Override
    public Review toEntity(ResultSet resultSet) {
        try {
            var review = new Review(
                    resultSet.getInt("id"),
                    resultSet.getString("todoid"),
                    resultSet.getDate("createdat"),
                    resultSet.getString("comentario"),
                    resultSet.getString("clientid")
            );

            return review;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public List<Review> getReview(String todoId) {
        var reviews = new ArrayList<Review>();

        try {

            var connect = this.connect();
            var statement = connect.prepareStatement(FIND_ALL_Reviews);
            statement.setString(1, todoId);
            var resultSet = this.query(statement);

            while(resultSet.next()) {
                var review = toEntity(resultSet);
                if(null != review) {
                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviews;
    }

    @Override
    public Review newReview(Review review) {
        return null;
    }

    @Override
    public boolean delReview(String todoId, String clientId) {
        try {
            var connection = this.connect();
            CallableStatement statement = connection.prepareCall(DELETE_REVIEW);
            statement.setString(1, todoId);
            statement.setString(2, clientId);


            var resultSet = this.execute(statement);
            //var rating = resultSet.getFloat("ratingAvg");

            //return rating;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public Review updateReview(String todoId, String clientid) {
        return null;
    }
}
