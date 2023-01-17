package org.example.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.model.Cat;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

public class AdvancedCatRepository implements CatRepository {
    private AdvancedCatRepository advancedCat;
    private Cat cat;
    private String dbUrl;
    private String tableName;
    private String db_Driver;
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:mem:test";
    private Connection connection = null;


    public AdvancedCatRepository(String dbUrl, String tableName) {
        this.dbUrl = dbUrl;
        this.tableName = tableName;
        this.connection = connectionDb();
    }

    public Connection connectionDb() {
         try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(DB_URL);
            config.setDriverClassName(DB_DRIVER);
            DataSource dataSource = new HikariDataSource(config);

            connection = dataSource.getConnection();
            System.out.println("Соединение с БД установдленно");

        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("ошибка SQL...!");
        }
        return connection;
    }

    /*public Function<ResultSet, List<Cat>> catsRowMapper = catList -> {
        try {
            while (catList.next()) {
                cat.setId(catList.getInt("id"));
                cat.setName(catList.getString("name"));
                cat.setWeight(catList.getInt("weight"));
                cat.setAngry(catList.getBoolean("isAngry"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cat;
    };*/
    public boolean createTable(AdvancedCatRepository advancedCat) {
        String createTableSQL = String.format("CREATE TABLE IF NOT EXISTS %s (id INT, Name VARCHAR(50)," +
                " Weight INT, isAngry boolean)", advancedCat.getTableName());
        try {
            System.out.println("Создание таблицы");
            Statement statement = connectionDb().createStatement();
            statement.executeUpdate(createTableSQL);

            System.out.println("БД - created ");
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL");
        }
        return true;
    }

    @Override
    public boolean create(Cat cat) {
        String createRowSQL = String.format("INSERT INTO %s (id,Name,Weight,isAngry) VALUES (%d, '%s', %d, %b);",
                tableName, cat.getId(), cat.getName(), cat.getWeight(), cat.isAngry());
        try {
            System.out.println("добавим кота");
            Statement statement = connectionDb().createStatement();
            statement.executeUpdate(createRowSQL);

            System.out.println("добавили кота =)");
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL");
        }
        return true;
    }

    @Override
    public Cat read(Integer id) {
        Cat cat = null;
        String readRowsSQL = String.format("SELECT * FROM %s WHERE id=%d", tableName, id);
        try {
            System.out.println("ПОЧЕТАЕМ кота =)");
            Statement statement = connectionDb().createStatement();
            statement.executeUpdate(readRowsSQL);

            System.out.println("Почетатели кота раошлись =)");
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL");
        }
        return cat;

    }

    @Override
    public int update(Integer id, Cat cat) {

        return 0;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Cat> findAll() {
        List<Cat> cats = new ArrayList<>();
        return cats;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}
