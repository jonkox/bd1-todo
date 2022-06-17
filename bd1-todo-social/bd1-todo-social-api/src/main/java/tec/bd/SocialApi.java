package tec.bd;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import spark.Request;
import spark.Response;
import tec.bd.Social.Rating;
import tec.bd.Social.authentication.Session;
import tec.bd.Social.authentication.SessionStatus;
import tec.bd.Social.repository.NewRating;

import java.util.Map;

import static spark.Spark.*;

/**
 * Hello world!
 *
 */
public class SocialApi
{
    public static void main(String[] args) {

        WebApplicationContext context = WebApplicationContext.init();
        var authenticationClient = context.getAuthenticationClient();
        var ratingsService = context.getRatingsService();
        var authenticationTodoRecord = context.getAuthenticationTodoRecord();
        var reviewsService = context.getReviewsServices();
        Gson gson = context.getGson();



        port(8082);


        // Autentication
        before((request, response) -> {

            var sessionParam = request.headers("x-session-id");
            var ratingAvg = authenticationTodoRecord.validateTodo(request.headers("x-session-id"), "todo-1");
            if(null == sessionParam) {
                halt(401, "Unauthorized");
            }
            var session = authenticationClient.validateSession(sessionParam);
            if(session.getStatus() == SessionStatus.INACTIVE) {
                halt(401, "Unauthorized");
            }
        });



        options("/", (request, response) -> {
            response.header("Content-Type", "application/json");
            return Map.of(
                    "message", "TODOS API V1");
        }, gson::toJson);




//        post("/ratings/:todo-id", (request, response) -> {
//
//            var sessionParam = request.headers("x-session-id");
//            var session = authenticationClient.validateSession(sessionParam);
//            var todoId = request.params("todo-id");
//            var ratingParams = gson.fromJson(request.body(), Rating.class);
//
//            ratingParams.setTodoId(todoId);
//            ratingParams.setClientId(session.getClientid());
//
//            System.out.println(session.getClientid());
//            System.out.println(sessionParam);
//
//            return null;
//        }, gson::toJson);


        post("/ratings/:todo-id", (request, response) -> {
            var sessionParam = request.headers("x-session-id");
            var session = authenticationClient.validateSession(sessionParam);
            var todoId = request.params("todo-id");
            var clientId = session.getClientid();
            var ratingParams = gson.fromJson(request.body(), Rating.class);
            ratingParams.setTodoId(todoId);
            ratingParams.setClientId(clientId);
            try {
                var rating = ratingsService.newRating(ratingParams);
                response.status(200);
                return rating;
            } catch (Exception e) {
                response.status(400);
                return Map.of("Message", "Bad Credentials");
            }
        }, gson::toJson);


        //Obtiene el valor promedio de los ratings de un todoId
        get("/ratings/:todo-id", (request, response) -> {
            var ratingIdParam = request.params("todo-id");


            try {
                var ratingId = Integer.parseInt(ratingIdParam);
                var rating = ratingsService.getRating(ratingId);

                if (null != rating)
                    return rating;
            }
            catch (Exception e){
                e.printStackTrace();
            }


            response.status(404);
            return Map.of();


//            response.header("Content-Type","application/json");
//            return Map.of(
//                    "message","Get ratign for todo-id  " + ratingId);


        },gson::toJson);



        get("todos/:todo-id/rating", (request, response) -> {
            var todoId = request.params("todo-id");

            var ratingAvg = ratingsService.getRatingAverage(todoId);
            response.status(200);


            response.header("Content-Type","application/json");
            return Map.of(
                    "rating",ratingAvg,
                    "todo-id",todoId );


        },gson::toJson);

//        //VALIDAR TODO
//        get("/ratings/:todo-id", (request, response) -> {
//            var todoId = request.params("todo-id");
//
//            var ratingAvg = authenticationTodoRecord.validateTodo()
//            response.status(200);
//
//
//            response.header("Content-Type","application/json");
//            return Map.of(
//                    "rating",ratingAvg,
//                    "todo-id",todoId );
//
//
//        },gson::toJson);


        delete("/ratings/:todo-id", (request, response) -> {
            var todoId = request.params("todo-id");
            var sessionParam = request.headers("x-session-id");
            var session = authenticationClient.validateSession(sessionParam);

            ratingsService.deleteRating(todoId,session.getClientid());

            response.status(200);


            response.header("Content-Type","application/json");
            return Map.of(
                    "todo-id",todoId );

        },gson::toJson);


        // -------------------------------------------------------------------------------------------------------------------------------------------------------------------

        get("/reviews/:todo-id", (request, response) -> {
            return reviewsService.getReview(request.params("todo-id"));
        }, gson::toJson);

        delete("/reviews/:todo-id", (request, response) -> {
            var todoId = request.params("todo-id");
            var sessionParam = request.headers("x-session-id");
            var session = authenticationClient.validateSession(sessionParam);

            reviewsService.delReview(todoId,session.getClientid());

            response.status(200);


            response.header("Content-Type","application/json");
            return Map.of(
                    "todo-id",todoId );

        },gson::toJson);

        put("/reviews/:todo-id", (request, response) -> {
            var todoId = request.params("todo-id");
            var sessionParam = request.headers("x-session-id");
            var session = authenticationClient.validateSession(sessionParam);

            reviewsService.updateReview(todoId,session.getClientid());

            response.status(200);


            response.header("Content-Type","application/json");
            return Map.of(
                    "todo-id",todoId );

        },gson::toJson);





    }
}
