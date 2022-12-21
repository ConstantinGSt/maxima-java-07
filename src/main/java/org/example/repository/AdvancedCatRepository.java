package org.example.repository;

import org.example.model.Cat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

public class AdvancedCatRepository implements CatRepository {
    String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    String proPath = rootPath + "db.properties";
    String catalogConfigPath = rootPath + "catalog";

    Properties appProps = new Properties();

    Properties catalogProps = new Properties();

    private SimpleCatRepository simpleCat;
    private Cat cat;
    private String dbUrl;
    private String tableName;
    private String db_Driver;

    public AdvancedCatRepository(String dbUrl, String tableName) {
        this.dbUrl = dbUrl;
        this.tableName = tableName;
    }

    public Function<ResultSet, List<Cat>> catsRowMapper = catList -> {

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
    };
    public boolean createTable(AdvancedCatRepository simpleCat) {
        String createTableSQL = String.format("CREATE TABLE %s (id INT, Name VARCHAR(50), Weight INT, isAngry boolean)", simpleCat.getTableName());
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
                cat = (Cat) catsRowMapper.apply(result);
                /*String name = result.getString("Name");
                int weight = result.getInt("Weight");
                boolean isAngry = result.getBoolean("isAngry");
                String template = (isAngry ? "Angry" : "Nonserious") + " Cat %s have weight %d kg.";
                System.out.println(String.format(template, name, weight));
                cat = new Cat(id, name, weight, isAngry);*/
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
                /*int id = result.getInt("id");
                String name = result.getString("Name");
                int weight = result.getInt("Weight");
                boolean isAngry = result.getBoolean("isAngry");*/
                Cat cat = catsRowMapper.apply(result);
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
