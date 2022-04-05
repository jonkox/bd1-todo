package tec.bd.repository.mysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import tec.bd.todo.repository.TodoRepository;
import tec.bd.todo.*;

public class TodoRepositoryMySqlImpl extends BaseRepository<TodoRecord> implements TodoRepository {


    private final static String CONNECTION_STRING = "jdbc:mysql://localhost:3306/todo";
    private final static String DB_USERNAME = "root";
    private final static String DB_PASSWORD = "diazcoto1112";

    private final static String SELECT_ALL_TODOS = "select tid, title, description, state, startdate, enddate from todorecord";
    private final static String SELECT_BY_STATUS =  "select tid, title, description, state, startdate, enddate from todorecord where state = ?";
    private final static String DELETE_BY_ID = "delete from todorecord where tid = ?";
    private final static String SELECT_BY_ID = "select tid, title, description, state, startdate, enddate from todorecord where tid = ?";
    private final static String INSERT_TODO = "insert into todorecord(tid, title, description, state, startdate, enddate) values (?, ?, ?, ?, ?, ?)";
    private final static String SELECT_BY_TEXT_IN_TITLE = "select * from todorecord where title like ?";



    @Override
    public List<TodoRecord> findAll() {

        var todoRecordList = new ArrayList<TodoRecord>();

        try {
            var resultSet = this.query(SELECT_ALL_TODOS);
            while(resultSet.next()) {
                var todoRecord = toEntity(resultSet);
                if(null != todoRecord) {
                    todoRecordList.add(todoRecord);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return todoRecordList;
    }

    @Override
    public List<TodoRecord> findAll(Status status) {

        var todoRecordList = new ArrayList<TodoRecord>();

        try {
            var connect = this.connect();
            var statement = connect.prepareStatement(SELECT_BY_STATUS);
            statement.setString(1, status.toString().toLowerCase());
            var resultSet = this.query(statement);
            while(resultSet.next()) {
                var todoRecord = toEntity(resultSet);
                if(null != todoRecord) {
                    todoRecordList.add(todoRecord);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return todoRecordList;
    }

    @Override
    public TodoRecord findById(String id) {
        try {
            var connect = this.connect();
            var statement = connect.prepareStatement(SELECT_BY_ID);
            statement.setString(1, id);
            var resultSet = this.query(statement);

            if(resultSet.next()) {
                return toEntity(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public TodoRecord save(TodoRecord todoRecord) {
        return null;
    }

    @Override
    public void remove(String id) {
        try {
            var connect = this.connect();
            var statement = connect.prepareStatement(DELETE_BY_ID);
            statement.setString(1, id);
            var actual = this.execute(statement);
            System.out.println("Actual: " + actual);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TodoRecord update(TodoRecord todoRecord) {
        return null;
    }

    @Override
    public List<TodoRecord> findByPatternInTitle(String textToSearch) {
        return null;
    }

    @Override
    public List<TodoRecord> findByBetweenStartDates(Date startDate, Date endDate) {
        return null;
    }

    @Override
    public TodoRecord toEntity(ResultSet resultSet) {
        try {
            var todoRecord = new TodoRecord(
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    Status.valueOf(resultSet.getString("state").toUpperCase()),
                    resultSet.getDate("startdate"),
                    resultSet.getDate("enddate")
            );
            todoRecord.setId(resultSet.getString("tid"));
            return todoRecord;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public List<TodoRecord> findByPatternInTitle(String textToSearch) {
        var todoRecordList = new ArrayList<TodoRecord>();

        try {
            try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, DB_USERNAME, DB_PASSWORD)) {

                try (Statement stmt = connection.createStatement()) {
                    //execute query -- consultas de seleccion
                    try (ResultSet resultSet = stmt.executeQuery(SELECT_BY_PATERN_IN_TITLE  + '%' + textToSearch.toLowerCase() +'%')) {
//                        System.out.println("ID\t\t Title\t\t Description\t\t Status \t\t Start Date \t\t End Date");
//                        System.out.println("-------------------------------------------------------------------------");
                        while(resultSet.next()) {

                            var todoRecord = new TodoRecord(
                                    resultSet.getString("title"),
                                    resultSet.getString("description"),
                                    Status.valueOf(resultSet.getString("state").toUpperCase()),
                                    resultSet.getDate("startdate"),
                                    resultSet.getDate("enddate")
                            );
                            todoRecord.setId(resultSet.getString("tid"));
                            todoRecordList.add(todoRecord);

                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Hubo un error");
        }

        return todoRecordList;
    }

    @Override
    public List<TodoRecord> findByBetweenStartDates(Date startDate, Date endDate) {
        return null;
    }

    @Override
    public TodoRecord update(TodoRecord todoRecord) {
        return null;
    }

}
