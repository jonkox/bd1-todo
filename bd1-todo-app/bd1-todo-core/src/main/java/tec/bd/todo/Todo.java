package tec.bd.todo;

import tec.bd.todo.repository.TodoRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
        try {
            if (id.equals(null))
                throw new IllegalArgumentException();

            else {
                return this.todoRepository.findById(id);

            }
        }catch (Exception e){
            e.printStackTrace();

        }

        return null;
    }

    public TodoRecord addTodoRecord(TodoRecord record) {
        Objects.requireNonNull(record);
        // Si el titulo es nulo, devolver exception
        try {
            if (record.getTitle().equals(null))
                throw new IllegalArgumentException();

            if(null == record.getStartDate()) {
                record.setStartDate(new Date());
            }
            record.setStatus(Status.NEW);

            return this.todoRepository.save(record);
        }catch (Exception e){
            e.printStackTrace();
        }

        return record;
    }

    public List<TodoRecord> getStartDateRange(Date startDate, Date endDate){
        List<TodoRecord> todoRecordList = this.todoRepository.findByBetweenStartDates(startDate,endDate);

        if (todoRecordList.isEmpty()){
            System.out.println("No existen TODOs dentro del rango de fechas.");
        }
        return todoRecordList;
    }

    public List<TodoRecord> searchInTitle(String textToSearch){
        return this.todoRepository.findByPatternInTitle(textToSearch);
    }

    public TodoRecord updateTodoRecord(TodoRecord todoRecord){
        Objects.requireNonNull(todoRecord);
        // TODO: si el todoRecord.Id no existe, lanzar exception
        var todo = this.todoRepository.findById(todoRecord.getId());

        // el registro a actualizar tiene que existir.
        // Si el titulo es nulo, devolver exception
        try {

            //Validacion de datos
            if (todo.equals(null))
                throw new IllegalArgumentException();

            if (todo.getTitle().isEmpty() || todo.getTitle().isBlank())
                throw new IllegalStateException();


            this.todoRepository.update(todoRecord);
        }catch (Exception e){
            e.printStackTrace();
        }
        return todoRecord;
    }

    public void deleteTodoRecord(String id){
        // TODO: buscar si el record existe, y si existe borrarlo

        try {
            var deletedToDo = this.todoRepository.findById(id);

            if (id.equals(null) || deletedToDo.equals(null))
                throw new IllegalArgumentException();

            this.todoRepository.remove(id);

        }catch (Exception e){

            e.printStackTrace();
        }





    }



}
