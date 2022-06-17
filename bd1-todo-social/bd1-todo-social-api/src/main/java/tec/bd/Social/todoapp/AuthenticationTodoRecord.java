package tec.bd.Social.todoapp;

public interface AuthenticationTodoRecord {

    TodoRecord validateTodo(String session,String todoId);

}
