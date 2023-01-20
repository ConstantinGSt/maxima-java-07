package org.example;

import com.zaxxer.hikari.HikariConfig;
import org.example.model.Cat;
import org.example.repository.AdvancedCatRepository;
import org.example.repository.SimpleCatRepository;

import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.util.Properties;

public class App {
    public static void main(String[] args) {

//        try {
//            String rootPath = Thread.currentThread().getContextClassLoader()
//                    .getResource("").getPath(); //resources/db.properties
//
//        String dbConfigPath = rootPath + "db.properties";
//        Properties dbProp = new Properties();
//        dbProp.load(new FileInputStream(dbConfigPath));
//
//        String appVersion = dbProp.getProperty("version");
//        } catch(NullPointerException e) {
//            System.out.println("ups");;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }


        System.out.println("Hello World!");
        Cat cat = new Cat(1, "Мурло", 4, true);
        Cat cat2 = new Cat(2,"Murka", 5,false);
        Cat cat3 = new Cat(3,"Barsik", 12,true);
        SimpleCatRepository simpleCat = new SimpleCatRepository("jdbc:h2:mem:test", "KisKis");

        AdvancedCatRepository adCat = new AdvancedCatRepository(AdvancedCatRepository.getDbConfigPath(), "myCat");
        adCat.createTable(adCat);
        adCat.create(cat);
        adCat.create(cat2);
        adCat.create(cat3);
        adCat.read(2);
        adCat.update(2, cat3);
        adCat.read(2);
        adCat.findAll();
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
