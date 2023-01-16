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
    String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    String proPath = rootPath + "db.properties";
    String catalogConfigPath = rootPath + "catalog";
    Properties appProps;
    Properties catalogProps;

    private AdvancedCatRepository advancedCat;
    private Cat cat;
    private String dbUrl;
    private String tableName;
    private String db_Driver;
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:mem:test";
    Connection connection;

    public AdvancedCatRepository(String dbUrl, String tableName) {
        this.dbUrl = dbUrl;
        this.tableName = tableName;
    }

    public void connectionDb() {
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
            advancedCat.connectionDb();
            System.out.println("Создание таблицы");
            Statement statement = connection.createStatement();
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

        return true;
    }

    @Override
    public Cat read(Integer id) {

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
