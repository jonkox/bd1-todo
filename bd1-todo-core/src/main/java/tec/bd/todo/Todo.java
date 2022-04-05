package tec.bd.todo;

import tec.bd.todo.repository.TodoRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Todo {

    private TodoRepository todoRepository;

    public Todo(TodoRepository todoRepo) {
        this.todoRepository = todoRepo;
    }

    public List<TodoRecord> getAll() {
        return this.todoRepository.findAll();
    }

    public List<TodoRecord> getAll(Status status) {
        return this.todoRepository.findAll(status);
    }

    public TodoRecord getById(String id) {
        // TODO: validar que el id no sea nulo y si es nulo lanzar una excepcion
        return this.todoRepository.findById(id);
    }

    public TodoRecord addTodoRecord(TodoRecord record) {
        Objects.requireNonNull(record);
        // Si el titulo es nulo, devolver exception
        if(null == record.getStartDate()) {
            record.setStartDate(new Date());
        }
        record.setStatus(Status.NEW);
        return this.todoRepository.save(record);
    }

    public TodoRecord update(TodoRecord record) {
        Objects.requireNonNull(record);

        // TODO: si el todoRecord.Id no existe, lanzar exception
        // el registro a actualizar tiene que existir.

        // Si el titulo es nulo, devolver exception
        if(null == record.getStartDate()) {
            record.setStartDate(new Date());
        }

        return this.todoRepository.update(record);
    }

    public void delete(String id) {

        // TODO: buscar si el record existe, y si existe borrarlo
        this.todoRepository.remove(id);
    }


    public List<TodoRecord> getStartDateRange(Date startDate, Date endDate){
        List<TodoRecord> todoRecordList = this.todoRepository.findByBetweenStartDates(startDate,endDate);

        if (todoRecordList.isEmpty()){
            System.out.println("No existen TODOs dentro del rango de fechas.");
        }
        return todoRecordList;
    }
    public List<TodoRecord> searchInTitle(String textToSearch){
        List<TodoRecord> todoRecordList = new ArrayList<>();

        return todoRecordList;
    }
    public TodoRecord updateTodoRecord(TodoRecord todoRecord){
        return todoRecord;
    }
    public void deleteTodoRecord(String id){
        var deletedToDo = this.todoRepository.findById(id);
    }



}
