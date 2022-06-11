package tec.bd;

import com.google.gson.Gson;
import tec.bd.authentication.SessionStatus;

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
        Gson gson = context.getGson();


        port(8082);


        // Autentication
        before((request, response) -> {

            var sessionParam = request.headers("x-session-id");
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


        get("/ratings/:todo-id", (request, response) -> {
            var todoId = request.params("todo-id");
            response.header("Content-Type","application/json");
            return Map.of(
                    "message","Get ratign for todo-id  " + todoId);


        },gson::toJson);


    }
}
