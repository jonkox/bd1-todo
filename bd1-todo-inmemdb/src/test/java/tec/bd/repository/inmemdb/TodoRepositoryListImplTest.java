package tec.bd.repository.inmemdb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import tec.bd.todo.Status;
import tec.bd.todo.TodoRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
                        Status.NEW, new Date(2022,02,12),new Date(2022,04,20)));
        repository.save(new TodoRecord("partido", "partido de amigos",
                        Status.NEW, new Date(2022,03,01),new Date(2022,04,20)));
        repository.save(new TodoRecord("clases", "clases el jueves",
                        Status.NEW, new Date(2022,01,20),new Date(2022,04,20)));



    }

    @Test
    public void findAll() throws Exception {




    }

    @Test
    public void findById() throws Exception {
        var hola = this.repository.findById("desayuno");
        System.out.println(hola);
    }

    @Test
    public void save() throws Exception {

    }

    @Test
    public void remove() throws Exception {

    }

    @Test
    public void findByPatternInTitle(){
        var hola = this.repository.findByPatternInTitle("as");
        for (TodoRecord i : hola) {
            System.out.println(i.getTitle());
        }

    }

    @Test
    public void findBetweenDates(){
        var results = this.repository.findByBetweenStartDates(new Date(2022,01,15), new Date(2022,04,02));

        System.out.println(results);
    }

    @Test
    public void update(){

    }


}
