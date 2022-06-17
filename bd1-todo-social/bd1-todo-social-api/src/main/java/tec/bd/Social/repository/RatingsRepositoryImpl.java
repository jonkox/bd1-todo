package tec.bd.Social.repository;

import tec.bd.Social.Rating;
import tec.bd.Social.datasource.DBManager;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingsRepositoryImpl extends BaseRepository<Rating> implements RatingsRepository{


    private static final String FIND_BY_RATING_ID_QUERY = "select id,todoid,ratingvalue,createdat,clientid from rating where id = ?";
    private static final String CALCULATE_AVG_PROC = "{CALL calculate_average_rating(?)}";
    private static final String DELETE_RATING = "delete from rating where todoid = ? and clientid = ?;";
    private static final String NEW_RATING = "{CALL insert_rating(?,?,?,?);}";

    public RatingsRepositoryImpl(DBManager dbManager){
        super(dbManager);
    }




    @Override
    public Rating findById(int ratingId) {
        try {
            var connection = this.connect();
            var statement = connection.prepareStatement(FIND_BY_RATING_ID_QUERY);
            statement.setInt(1, ratingId);
            var resultSet = this.query(statement);
            while(resultSet.next()) {
                var rating = toEntity(resultSet);
                return rating;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public float findAverage(String todoId) {
        try {
            var connection = this.connect();
            CallableStatement statement = connection.prepareCall(CALCULATE_AVG_PROC);
            statement.setString(1,todoId);


            var resultSet = this.query(statement);
            //var rating = resultSet.getFloat("ratingAvg");

            while(resultSet.next()) {
                var ratingAvg = resultSet.getFloat("ratingAvg");
                return ratingAvg;
            }

            //return rating;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0f;
    }

    @Override
    public boolean deleteRating(String todoId, String clientId) {
        try {
            var connection = this.connect();
            CallableStatement statement = connection.prepareCall(DELETE_RATING);
            statement.setString(1, todoId);
            statement.setString(2,clientId);


            var resultSet = this.execute(statement);
            //var rating = resultSet.getFloat("ratingAvg");

            //return rating;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public Rating newRating(Rating newRating) {
        try {
            var connection = this.connect();
            CallableStatement statement = connection.prepareCall(NEW_RATING);



            var resultSet = this.query(statement);
            //var rating = resultSet.getFloat("ratingAvg");

//            while(resultSet.next()) {
//                var ratingAvg = resultSet.getFloat("ratingAvg");
//                return ratingAvg;
//            }

            //return rating;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public Rating toEntity(ResultSet resultSet) {
        try {
            var rating = new Rating(
                    resultSet.getInt("id"),
                    resultSet.getInt("ratingValue"),
                    resultSet.getString("todoid"),
                    resultSet.getString("clientid"),
                    resultSet.getDate("createdat")
                    );

            return rating;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
