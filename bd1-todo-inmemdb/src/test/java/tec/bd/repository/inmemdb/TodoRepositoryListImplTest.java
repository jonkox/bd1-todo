package tec.bd.repository.inmemdb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import tec.bd.todo.Status;
import tec.bd.todo.TodoRecord;

import javax.sound.midi.Soundbank;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

public class TodoRepositoryListImplTest {
    TodoRepositoryListImpl repository;




    @BeforeEach
    public void  setup(){

        List<TodoRecord> list = new ArrayList<>();
        this.repository = new TodoRepositoryListImpl(list);

        repository.save(new TodoRecord("desayuno", "desayuno de la familia",
                        Status.NEW, new Date(2022,01,15),new Date(2022,04,20)));
        repository.save(new TodoRecord("almuerzo", "almuerzo de la familia",
                        Status.NEW, new Date(2022,02,10),new Date(2022,04,20)));
        repository.save(new TodoRecord("notas", "notas del proyecto",
                        Status.BLOCKED, new Date(2022,02,12),new Date(2022,04,20)));
        repository.save(new TodoRecord("partido", "partido de amigos",
                        Status.NEW, new Date(2022,03,01),new Date(2022,04,20)));
        repository.save(new TodoRecord("clases", "clases el jueves",
                        Status.NEW, new Date(2022,01,20),new Date(2022,04,20)));



    }

    @Test
    public void findAll() throws Exception {
        var todoRecords = this.repository.findAll();

        if (todoRecords.isEmpty())
            System.out.println("Aun no hay TODOs");

        else {
            for (TodoRecord i : todoRecords) {
            System.out.println(i.getTitle());
            }
        }

    }

    @Test
    public void findByStatus(){
        var todoRecords = this.repository.findAll(Status.BLOCKED);

        if (todoRecords.isEmpty())
            System.out.println("Aun no hay TODOs");

        else {
            for (TodoRecord i : todoRecords) {
                System.out.println(i.toString());
            }
        }


    }

    @Test
    public void findById() throws Exception {
        var id = this.repository.save(new TodoRecord("Carrera")).getId();

        if (id.equals(null))
            System.out.println("El TODO no existe");
        else{
            System.out.println(this.repository.findById(id));
        }



    }

    @Test
    public void save() throws Exception {
        var before = this.repository.findAll().size();

        this.repository.save(mock(TodoRecord.class));

        var after = this.repository.findAll().size();

        assertThat(before).isLessThan(after);

    }

    @Test
    public void remove() throws Exception {
        var id = this.repository.save(new TodoRecord("Carrera")).getId();

        var todos = this.repository.findAll();
        System.out.println("Cantidad de TODOs antes de la eliminacion = " + todos.size());

        this.repository.remove(id);

        var todos2 = this.repository.findAll();
        System.out.println("Cantidad de TODOs despues de la eliminacion = " + todos2.size());



    }

    @Test
    public void findByPatternInTitle(){
        var list = this.repository.findByPatternInTitle("as");
        for (TodoRecord i : list) {
            System.out.println(i.getTitle());
        }

    }

    @Test
    public void findBetweenDates(){
        var results = this.repository.findByBetweenStartDates(new Date(2022,01,15), new Date(2022,04,02));

        //assertThat(results).isNotEmpty();

        for (TodoRecord i : results) {
            System.out.println(i.getTitle());
        }
    }

    @Test
    public void update(){
        var id = this.repository.save(new TodoRecord("Carrera"));

        var todo = this.repository.update(id);

        System.out.println(todo.toString());

    }
    @Test
    public void updateTodoRecord() throws Exception{

        //TODOS DE EJEMPLO
        var todoRecord = this.repository.save(new TodoRecord("carrera","recordar la nueva carrera",
                                                Status.NEW,new Date(2022,01,01),new Date(2022,01,01)));

        var todo = this.repository.findById(todoRecord.getId());

        var newTodoRecord = this.repository.save(new TodoRecord("comida","comprar los ingredientes",
                Status.BLOCKED,new Date(2022,12,24),new Date(2022,12,31)));


        //MOSTRAR LA INFORMACION DEL TODO VIEJO
        System.out.println(todo.getId());
        System.out.println(todo.getTitle());
        System.out.println(todo.getDescription());
        System.out.println(todo.getStatus());
        System.out.println(todo.getStartDate());
        System.out.println(todo.getEndDate());


        // el registro a actualizar tiene que existir.

        // Si el titulo es nulo, devolver exception
        try {

            //region validaciones
            //Validacion de datos
            if (newTodoRecord.equals(null))
                throw new IllegalArgumentException();

            if (newTodoRecord.getTitle().isEmpty() || newTodoRecord.getTitle().isBlank())
                throw new IllegalStateException();

            //endregion

            //Setear la nueva informacion
            todo.setTitle(newTodoRecord.getTitle());
            todo.setDescription(newTodoRecord.getDescription());
            todo.setStatus(newTodoRecord.getStatus());
            todo.setStartDate(newTodoRecord.getStartDate());
            todo.setEndDate(newTodoRecord.getEndDate());

            if(null == todoRecord.getStartDate()) {
                todoRecord.setStartDate(new Date());
            }

            //Mostrar la nueva informacion del todo
            System.out.println("**************************************************************************");
            System.out.println(todo.getId());
            System.out.println(todo.getTitle());
            System.out.println(todo.getDescription());
            System.out.println(todo.getStatus());
            System.out.println(todo.getStartDate());
            System.out.println(todo.getEndDate());

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
