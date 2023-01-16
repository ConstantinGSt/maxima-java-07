package org.example.repository;

import org.example.model.Cat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SimpleCatRepository implements CatRepository {
    //private SimpleCatRepository simpleCat;
    private Cat cat;
    private String dbUrl;
    private String tableName;
    private static final String DB_DRIVER = "org.h2.Driver";

    public SimpleCatRepository(String dbUrl, String tableName) {
        this.dbUrl = dbUrl;
        this.tableName = tableName;
    }

    public boolean createTable(SimpleCatRepository simpleCat) {
        String createTableSQL = String.format("CREATE TABLE CREATE TABLE IF NOT EXISTS %s (id INT, Name VARCHAR(50), Weight INT, isAngry boolean)", simpleCat.getTableName());
        String selectTableSQL = String.format("SELECT * FROM %s", simpleCat.getTableName());
        try {
            Class.forName(DB_DRIVER);
            Connection connect = DriverManager.getConnection(simpleCat.getDbUrl());
            System.out.println("Соединение с БД установдленно - создание таблицы");

            Statement statement = connect.createStatement();
            statement.executeUpdate(createTableSQL);

            System.out.println("Соединение с БД НЕ ЗАКРЫТО! - иначе пустая таблица не сохранится =(");
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
        String createRowSQL = String.format("INSERT INTO %s (id,Name,Weight,isAngry) VALUES (%d, '%s', %d, %b);",
                tableName, cat.getId(), cat.getName(), cat.getWeight(), cat.isAngry());
        String selectTableSQL = String.format("SELECT * FROM %s", tableName);
       // System.out.println(createRowSQL + "\n" + selectTableSQL);
        try {
            Class.forName(DB_DRIVER);
            Connection connect = DriverManager.getConnection(dbUrl);
            System.out.println("Соединение с БД установдленно - создание строки");

            Statement statement = connect.createStatement();
            statement.executeUpdate(createRowSQL); //"INSERT INTO KisKis (id,Name,Weight, isAngry) VALUES (1, 'Мурло', 4, true);"

            connect.close();
            System.out.println("Соединение с БД ЗАКРЫТО!- изменения внесены");
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
        String readRowsSQL = String.format("SELECT * FROM %s WHERE id=%d", tableName, id);
        try {
            Class.forName(DB_DRIVER);
            Connection connect = DriverManager.getConnection(dbUrl);
            System.out.println("Соединение с БД установдленно - чтение по id");

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
            System.out.println("Соединение с БД ЗАКРЫТО!- если было пусто значит id не существует");
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
    public int update(Integer id, Cat cat) {
        int rows = 0;
        String updateRowsSQL = String.format("UPDATE %s SET Name='%s' WHERE id=%d", tableName, cat.getName(), id);
        try {
            Class.forName(DB_DRIVER);
            Connection connect = DriverManager.getConnection(dbUrl);
            System.out.println("Соединение с БД установдленно - UPDATE");

            Statement statement = connect.createStatement();
            rows = statement.executeUpdate(updateRowsSQL);

            connect.close();
            System.out.println("Соединение с БД ЗАКРЫТО! ");
        } catch (ClassNotFoundException q) {
            q.printStackTrace();
            System.out.println("нет драйвера");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL");
        }
        return rows;
    }

    @Override
    public void delete(Integer id) {
        String deleteRowsSQL = String.format("DELETE FROM %s WHERE id=%d", tableName, id);
        try {
            Class.forName(DB_DRIVER);
            Connection connect = DriverManager.getConnection(dbUrl);
            System.out.println("Соединение с БД установдленно - DELETE");

            Statement statement = connect.createStatement();
            statement.executeUpdate(deleteRowsSQL);

            connect.close();
            System.out.println("Соединение с БД ЗАКРЫТО!");
        } catch (ClassNotFoundException q) {
            q.printStackTrace();
            System.out.println("нет драйвера");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL");
        }
    }

    @Override
    public List<Cat> findAll() {
        ArrayList<Cat> list = new ArrayList<>();
        String readRowsSQL = String.format("SELECT * FROM %s", tableName);
        try {
            Class.forName(DB_DRIVER);
            Connection connect = DriverManager.getConnection(dbUrl);
            System.out.println("Соединение с БД установдленно - Ищем все!");

            Statement statement = connect.createStatement();
            ResultSet result = statement.executeQuery(readRowsSQL);

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
            connect.close();
            System.out.println("Соединение с БД ЗАКРЫТО! - был выведен смисок ID присутвующих в таблице строк");
        } catch (ClassNotFoundException q) {
            q.printStackTrace();
            System.out.println("нет драйвера");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL");
        }

        return list;
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
