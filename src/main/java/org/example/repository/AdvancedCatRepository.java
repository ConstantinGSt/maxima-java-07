package org.example.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.model.Cat;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

public class AdvancedCatRepository implements CatRepository {
    private Cat cat;
    private String dbUrl;
    private String tableName;

    private static String rootPath = Thread.currentThread().getContextClassLoader()
            .getResource("").getPath(); // по хорошему вынести dbConfig
    private static String dbConfigPath = rootPath + "db.properties"; // по хорошему вынести dbConfig
    private Properties dbProp = new Properties(); //// по хорошему вынести dbConfig
    private Connection connection = null;

    public AdvancedCatRepository(String dbUrl, String tableName) {
        this.dbUrl = dbUrl;
        this.tableName = tableName;
        this.connection = connectionDb();
    }

    private Connection connectionDb() {
        try {
            dbProp.load(new FileInputStream(dbConfigPath));

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dbProp.getProperty("db.url"));
            config.setDriverClassName(dbProp.getProperty("db.driver"));
            DataSource dataSource = new HikariDataSource(config);

            connection = dataSource.getConnection();
            System.out.println("Соединение с БД установдленно");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ошибка SQL...!");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    private Function<ResultSet, Cat> catRowMap = resultSet -> {
        try {
            return new Cat(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("weight"),
                    resultSet.getBoolean("isAngry")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    private Function<ResultSet, List<Cat>> catsRMList = catsList -> {
        List<Cat> cats = new ArrayList<>();
        try {
            while (catsList.next()) {
                cat = new Cat(
                        catsList.getInt("id"),
                        catsList.getString("name"),
                        catsList.getInt("weight"),
                        catsList.getBoolean("isAngry")
                );
                cats.add(cat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cats;
    };

    public boolean createTable(AdvancedCatRepository advancedCat) {
        String createTableSQL = String.format("CREATE TABLE IF NOT EXISTS %s (id INT, Name VARCHAR(50)," +
                " Weight INT, isAngry boolean)", advancedCat.getTableName());
        try {
            System.out.println("Создание таблицы");
            Statement statement = getConnection().createStatement();
            statement.executeUpdate(createTableSQL);

            System.out.println("БД - created ");
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
        try {
            System.out.println("добавим кота");
            Statement statement = getConnection().createStatement();
            statement.executeUpdate(createRowSQL);

            System.out.println("добавили кота =)");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL");
        }
        return true;
    }

    @Override
    public Cat read(Integer id) {
        cat = null;
        String readRowsSQL = String.format("SELECT * FROM %s WHERE id=%d", tableName, id);
        try {
            System.out.println("ПОЧЕТАЕМ кота =)");
            Statement statement = getConnection().createStatement();
            ResultSet result = statement.executeQuery(readRowsSQL);

            while (result.next()) {
                cat = catRowMap.apply(result);
                String template = (cat.isAngry() ? "Angry" : "not angry:") + " Cat %s have weight %d kg.";
                System.out.println(String.format(template, cat.getName(), cat.getWeight()));
            }
            return cat;
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
            System.out.println("изменим кота");
            Statement statement = getConnection().createStatement();
            rows = statement.executeUpdate(updateRowsSQL);

            System.out.println("изменили кота =)");
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
            System.out.println("добавим кота");
            Statement statement = getConnection().createStatement();
            statement.executeUpdate(deleteRowsSQL);

            System.out.println("добавили кота =)");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL");
        }
    }

    @Override
    public List<Cat> findAll() {
        List<Cat> list = null;
        String readAllRowsSQL = String.format("SELECT * FROM %s", tableName);
        try {
            System.out.println("ПОЧЕТАЕМ котов =)");
            Statement statement = getConnection().createStatement();
            ResultSet result = statement.executeQuery(readAllRowsSQL);

            list = catsRMList.apply(result);
            for (Cat cat : list) {
                System.out.println(cat.getId() + " " + cat.getName() + " " + cat.getWeight());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL");
        }
        return list;
    }

    public Connection getConnection() {
        return connection;
    }

    public String getTableName() {
        return tableName;
    }

    public static String getDbConfigPath() {  // по хорошему вынести dbConfig
        return dbConfigPath;
    }
}
