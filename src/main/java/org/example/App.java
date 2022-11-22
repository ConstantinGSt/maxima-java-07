package org.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class App {

    public static final String DB_URL = "jdbc:h2:mem:test";
    public static final String DB_DRIVER = "org.h2.Driver";


    public static void main(String[] args) {
        System.out.println("Hello World!");

        try {
            Class.forName(DB_DRIVER);
            Connection connect = DriverManager.getConnection(DB_URL);
            System.out.println("Соединение с БД установдленно");

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
