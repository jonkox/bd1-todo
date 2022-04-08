package tec.bd.controller;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import tec.bd.Message;
import tec.bd.todo.Status;
import tec.bd.todo.Todo;
import tec.bd.todo.TodoRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TodoController {

    private static Gson GSON = new Gson();

    private final Todo todo;

    public TodoController(Todo todo) {
        this.todo = todo;
    }

    public List<TodoRecord> getAllTodos(Request request, Response response) {
        var todos = this.todo.getAll() ;
        if(todos.isEmpty()) {
            response.status(204);
        }
        response.header("Content-Type", "application/json");
        return todos;
    }

    public TodoRecord getTodo(Request request, Response response) {

        var todoId = request.params("todo-id");
        // TODO: Si el todoId es nulo lanzar exception o devolver 404

        var todoRecord = this.todo.getById(todoId);
        if(null != todoRecord) {
            response.status(404);
        }
        response.header("Content-Type", "application/json");
        return todoRecord;
    }

    public List<TodoRecord> getTodosByStatus(Request request, Response response) {

        var status = request.params("todo-status");
        // TODO: Si el status es nulo lanzar exception o devolver 404

        if (status.equals(null))
            response.status(404);

        var todoStatus = Status.valueOf(status.toUpperCase());

        var todoRecords = this.todo.getAll(todoStatus);
        if(todoRecords.isEmpty()) {
            response.status(404);
        }
        response.header("Content-Type", "application/json");
        return todoRecords;
    }

    public TodoRecord createTodoRecord(Request request, Response response) {

        var todoRecord = GSON.fromJson(request.body(), TodoRecord.class);
        //TODO: si hay una exception capturar y retornar 500

        var newTodo = this.todo.addTodoRecord(todoRecord);

        if (newTodo.equals(TodoRecord.class)){
            response.status(500);
        }
        else {

            response.header("Content-Type", "application/json");
            response.header("Location", "/todos/" + newTodo.getId());
            response.status(201);
        }
        return newTodo;

    }

    public Message deleteTodoRecord(Request request, Response response) {
        var todoId = request.params("todo-id");
        // TODO: Si el todoId es nulo lanzar exception o devolver 404

        // TODO: poner try/catch aqui porque si el borrado falla no deberia retornar 200-OK
        this.todo.deleteTodoRecord(todoId);
        response.status(200);
        return new Message(200, "OK");
    }

    public TodoRecord updatedTodoRecord(Request request, Response response) {

        var todoRecord = GSON.fromJson(request.body(), TodoRecord.class);
        //TODO: si hay una exception capturar y retornar 500

        var newTodo = this.todo.updateTodoRecord(todoRecord);

        response.header("Content-Type", "application/json");
        response.header("Location", "/todos/" + newTodo.getId());
        response.status(200);
        return newTodo;

    }

    public List<TodoRecord> searchInTitle(Request request, Response response) {

        var textToSearch = request.queryParams("q");
        var results = this.todo.searchInTitle(textToSearch);
        response.header("Content-Type", "application/json");

        return results;
    }

    public List<TodoRecord> startDateRange(Request request, Response response) {
        var start = request.queryParams("start");
        var end = request.queryParams("end");

        var formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            var startDate = formatter.parse(start);
            var endDate = formatter.parse(end);
            var results = this.todo.getStartDateRange(startDate,endDate);
            response.header("Content-Type", "application/json");

            return results;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
