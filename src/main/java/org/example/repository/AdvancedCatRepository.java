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
            Statement statement = getConnection().createStatement();
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
            Statement statement = getConnection().createStatement();
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
            Statement statement = getConnection().createStatement();
            ResultSet result = statement.executeQuery(readRowsSQL);

            while (result.next()) {
                String name = result.getString("Name");
                int weight = result.getInt("Weight");
                boolean isAngry = result.getBoolean("isAngry");
                String template = (isAngry ? "Angry" : "not angry:") + " Cat %s have weight %d kg.";
                System.out.println(String.format(template, name, weight));
                cat = new Cat(id, name, weight, isAngry);
            }
            return cat;

        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL");
        } System.out.println("Почетатели кота разошлись =)");
        return cat;

    }

    @Override
    public int update(Integer id, Cat cat) {
        int rows = 0;
        String updateRowsSQL = String.format("UPDATE %s SET Name='%s' WHERE id=%d", tableName, cat.getName(), id);
        try {
            System.out.println("изменим кота");
            Statement statement = getConnection().createStatement();
            rows = statement.executeUpdate(updateRowsSQL);

            System.out.println("изменили кота =)");
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL");
        }
        return rows;
    }

    @Override
    public void delete(Integer id) {
        String deleteRowsSQL = String.format("DELETE FROM %s WHERE id=%d", tableName, id);
        try {
            System.out.println("добавим кота");
            Statement statement = getConnection().createStatement();
            statement.executeUpdate(deleteRowsSQL);

            System.out.println("добавили кота =)");
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL");
        }
    }

    @Override
    public List<Cat> findAll() {
        ArrayList<Cat> list = new ArrayList<>();
        String readAllRowsSQL = String.format("SELECT * FROM %s", tableName);
        try {
            System.out.println("ПОЧЕТАЕМ кота =)");
            Statement statement = getConnection().createStatement();
            ResultSet result = statement.executeQuery(readAllRowsSQL);

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("Name");
                int weight = result.getInt("Weight");
                boolean isAngry = result.getBoolean("isAngry");
                cat = new Cat(id, name, weight, isAngry);
                list.add(cat);
            }
            for (Cat cat: list) {
                System.out.println(cat.getId());
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL");
        }
        return list;
    }

    public Connection getConnection() {
        return connection;
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
