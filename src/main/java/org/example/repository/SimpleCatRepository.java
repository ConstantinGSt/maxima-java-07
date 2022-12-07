package org.example.repository;

import org.example.model.Cat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class SimpleCatRepository implements CatRepository {
    SimpleCatRepository simpleCat;
    private String dbUrl;
    private String tableName;
    private static final String DB_DRIVER = "org.h2.Driver";

    public SimpleCatRepository(String dbUrl, String tableName) {
        this.dbUrl = dbUrl;
        this.tableName = tableName;
    }

    public boolean createTable(SimpleCatRepository simpleCat) {
        String createTableSQL = String.format("CREATE TABLE %s (Name VARCHAR(50), Weight INT, isAngry boolean)", simpleCat.getTableName());
        String selectTableSQL = String.format("SELECT * FROM %s", simpleCat.getTableName());
        System.out.println(createTableSQL + "\n" + selectTableSQL);
        try {
            Class.forName(DB_DRIVER);
            Connection connect = DriverManager.getConnection(simpleCat.getDbUrl());
            System.out.println("Соединение с БД установдленно");

            Statement statement = connect.createStatement();
            statement.executeUpdate(createTableSQL);

            ResultSet result = statement.executeQuery(selectTableSQL);
            while (result.next()) {
                String name = result.getString("Name");
                int weight = result.getInt("Weight");
                boolean isAngry = result.getBoolean("isAngry");
                String template = (isAngry ? "Angry" : "Nonserious") + " Cat %s have weight %d kg.";
                System.out.println(String.format(template, name, weight));
            }
            connect.close();
            System.out.println("Соединение с БД ЗАКРЫТО!");

        } catch (ClassNotFoundException q) {
            q.printStackTrace();
            System.out.println("нет драйвера");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL");
        }
        return true;
    }

    @Override
    public boolean create(Cat cat) {
        String createRowSQL = String.format("INSERT INTO %s (id,Name,Weight,isAngry) VALUES (%d, %s, %d, %b)",
                                                tableName, cat.getId(), cat.getName(), cat.getWeight(), cat.isAngry());
        String selectTableSQL = String.format("SELECT * FROM %s", tableName);
        System.out.println(createRowSQL + "\n" + selectTableSQL);
        try {
            Class.forName(DB_DRIVER);
            Connection connect = DriverManager.getConnection(dbUrl);
            System.out.println("Соединение с БД установдленно");

            Statement statement = connect.createStatement();
            statement.executeUpdate(createRowSQL);

            connect.close();
            System.out.println("Соединение с БД ЗАКРЫТО!");
        } catch (ClassNotFoundException q) {
            q.printStackTrace();
            System.out.println("нет драйвера");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL");
        }
        return true;
    }

    @Override
    public Cat read(Integer id) {
        Cat cat = null;
        String readRowsSQL = String.format("SELECT %d FROM cats", id);
        try {
            Class.forName(DB_DRIVER);
            Connection connect = DriverManager.getConnection(dbUrl);
            System.out.println("Соединение с БД установдленно");

            Statement statement = connect.createStatement();
            ResultSet result = statement.executeQuery(readRowsSQL);

            while (result.next()) {
                String name = result.getString("Name");
                int weight = result.getInt("Weight");
                boolean isAngry = result.getBoolean("isAngry");
                String template = (isAngry ? "Angry" : "Nonserious") + " Cat %s have weight %d kg.";
                System.out.println(String.format(template, name, weight));
                cat = new Cat(id, name, weight, isAngry);
            }

            connect.close();
            System.out.println("Соединение с БД ЗАКРЫТО!");
        } catch (ClassNotFoundException q) {
            q.printStackTrace();
            System.out.println("нет драйвера");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL");
        }
        return cat;

    }

    @Override
    public int update(Integer id, Cat value) {
        return 0;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Cat> findAll() {
        return null;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
