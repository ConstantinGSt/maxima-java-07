package org.example;

import org.example.model.Cat;
import org.example.repository.SimpleCatRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class App {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Cat cat = new Cat(1, "Мурло", 4, true);
        Cat cat2 = new Cat(2,"Murka", 5,false);
        Cat cat3 = new Cat(3,"Barsik", 12,true);
        SimpleCatRepository simpleCat = new SimpleCatRepository("jdbc:h2:mem:test", "KisKis");
        /*simpleCat.createTable(simpleCat);
        simpleCat.create(cat);
        simpleCat.read(1);
        cat.setName("Мурлыка");
        simpleCat.update(1, cat);
        simpleCat.read(1);

        simpleCat.create(cat2);
        simpleCat.create(cat3);

        simpleCat.read(3);
        simpleCat.delete(2);
        simpleCat.read(2);

        simpleCat.findAll();*/

    }
}
