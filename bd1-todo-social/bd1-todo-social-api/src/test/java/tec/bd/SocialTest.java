package tec.bd;


import com.google.gson.Gson;

import java.util.Map;

import static spark.Spark.*;


public class SocialTest
{
    public static void main(String[] args) {

        Gson gson = new Gson();


        port(8082);

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
