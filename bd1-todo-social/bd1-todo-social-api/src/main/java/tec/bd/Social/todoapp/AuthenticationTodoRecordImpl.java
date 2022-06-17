package tec.bd.Social.todoapp;

import tec.bd.Social.authentication.Session;
import tec.bd.Social.authentication.SessionStatus;

public class AuthenticationTodoRecordImpl implements AuthenticationTodoRecord{
    private tec.bd.Social.todoapp.AuthenticationResource authenticationResource;

    public AuthenticationTodoRecordImpl(AuthenticationResource authenticationResource) {
        this.authenticationResource = authenticationResource;
    }


    @Override
    public TodoRecord validateTodo(String session, String todoId) {
        try {
            return authenticationResource.validateTodo(session,todoId).execute().body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
