package tec.bd.repository.inmemdb;

import tec.bd.todo.Status;
import tec.bd.todo.TodoRecord;
import tec.bd.todo.repository.TodoRepository;

import java.util.*;

public class TodoRepositoryListImpl implements TodoRepository {

    private List<TodoRecord> todoData;

    public TodoRepositoryListImpl(List<TodoRecord> todoData) {
        this.todoData = todoData;
    }

    @Override
    public List<TodoRecord> findAll() {
        return this.todoData;
    }

    @Override
    public List<TodoRecord> findAll(Status status) {
        return null;
    }

    @Override
    public TodoRecord findById(String id) {
        var todoRecord = this.todoData
                .stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();

        return todoRecord.orElse(null);
    }

    @Override
    public TodoRecord save(TodoRecord todoRecord) {
        todoRecord.setId(UUID.randomUUID().toString());
        this.todoData.add(todoRecord);
        return todoRecord;
    }

    @Override
    public void remove(TodoRecord todoRecord) {

    }

    @Override
    public List<TodoRecord> findByPatternInTitle(String textToSearch) {

        List<TodoRecord> list = new ArrayList<>();

        //Ciclo que busca si el titulo contiene la cadena que viene como parametro
        for (TodoRecord i:this.todoData) {
            if (i.getTitle().contains(textToSearch))
                list.add(i);
        }

        return list;

    }

    @Override
    public List<TodoRecord> findByBetweenStartDates(Date startDate, Date endDate) {
        List<TodoRecord> list = new ArrayList<>();

        for (TodoRecord i:this.todoData) {
            if (i.getStartDate().before(endDate) && i.getStartDate().after(startDate))
                list.add(i);
            }

        return list;
    }

    @Override
    public TodoRecord update(TodoRecord todoRecord) {
        var todo = findById(todoRecord.getId());


        todo.setStatus(todoRecord.getStatus());
        todo.setTitle(todoRecord.getTitle());
        todo.setStatus(todoRecord.getStatus());


        return null;
    }

}
