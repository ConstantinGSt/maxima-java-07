package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class App {

    public static final String DB_URL = "jdbc:h2:mem:test";
    public static final String DB_DRIVER = "org.h2.Driver";


    public static void main(String[] args) {
        System.out.println("Hello World!");

        try {
            Class.forName(DB_DRIVER);
            Connection connect = DriverManager.getConnection(DB_URL);
            System.out.println("Соединение с БД установдленно");

            Statement statement = connect.createStatement();
            statement.executeUpdate("CREATE TABLE cats (Name VARCHAR(50), Weight INT)");
            statement.executeUpdate("INSERT INTO cats(Name,Weight) VALUES ('Mursic', 10)");
            statement.executeUpdate("INSERT INTO cats(Name,Weight) VALUES ('Ramzes', 2)");
            statement.executeUpdate("INSERT INTO cats(Name,Weight) VALUES ('Karl', 7)");
            statement.executeUpdate("INSERT INTO cats(Name,Weight) VALUES ('Ramzes', 12)");

            int rows = statement.executeUpdate("UPDATE cats SET name='Viskass' WHERE Name = 'Ramzes'");
            System.out.println("Update rows " + rows);

            rows = statement.executeUpdate("DELETE FROM cats WHERE Weight=7");
            System.out.println("Delete rows " + rows);

            statement.executeUpdate("ALTER TABLE cats ADD isAngry BIT");
            rows = statement.executeUpdate("UPDATE cats SET isAngry=true WHERE Weight<10");
            System.out.println("Update isAngry rows " + rows);

            ResultSet result = statement.executeQuery("SELECT * FROM cats");
            while(result.next()) {
                String name = result.getString("Name");
                int weight = result.getInt("Weight");
                boolean isAngry = result.getBoolean("isAngry");
                String template = (isAngry ? "Angry" : "Nonserious")+ " Cat %s have weight %d kg.";
                System.out.println(String.format(template, name, weight));
            }
            connect.close();
            System.out.println("Соединение с БД ЗАКРЫТО!");
        } catch (ClassNotFoundException q) {
            q.printStackTrace();
            System.out.println("нет драйвера");
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL");
        }
    }
}
