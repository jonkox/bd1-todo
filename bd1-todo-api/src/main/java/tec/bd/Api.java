package tec.bd;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static spark.Spark.*;

public class Api
{
    private final static Logger LOG = LoggerFactory.getLogger(Api.class);

    public static void main( String[] args )
    {

        var webAppContext = WebApplicationContext.init();
        Gson gson = webAppContext.getGson();
        var todoController = webAppContext.getTodoController();


        options("/", (request, response) -> {response.header("Content-Type", "applications/json");
            return Map.of("message", "TODOS API V1",
                    "find-all","/api/v1/todos",
                    "find-by-status","/api/v1/todos/{status}");}, gson::toJson);

        path("/api/v1/todos", () -> {
            before("/*", (q, a) -> LOG.info("Received api call"));
            get("", todoController::getAllTodos, gson::toJson);
            get("/", todoController::getAllTodos, gson::toJson);

            get("/status/:todo-status", todoController::getTodosByStatus, gson::toJson);

            get("/:todo-id", todoController::getTodo, gson::toJson);

            post("", "application/json", (request, response) -> todoController.createTodoRecord(request, response), gson::toJson);

//            delete("", "application/json", (request, response) -> todoController.createTodoRecord(request, response), gson::toJson);
//
//            put("", "application/json", (request, response) -> todoController.createTodoRecord(request, response), gson::toJson);

        });


    }



}
