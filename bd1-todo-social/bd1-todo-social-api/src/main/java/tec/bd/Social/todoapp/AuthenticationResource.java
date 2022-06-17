package tec.bd.Social.todoapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface AuthenticationResource {

    @GET("api/v1/todos/{todo-id}")
    Call<TodoRecord> validateTodo(@Header("x-session-id")String sessionId, @Path("todo-id") String todoId);
}
