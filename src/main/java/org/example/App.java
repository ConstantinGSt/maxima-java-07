package org.example;

import com.zaxxer.hikari.HikariConfig;
import org.example.config.SpringConfig;
import org.example.model.Cat;
import org.example.repository.AdvancedCatRepository;
import org.example.repository.SimpleCatRepository;
import org.example.repository.SpringCatRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.util.Properties;

public class App {
    public static void main(String[] args) {

        System.out.println("Hello World!");

        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        SpringCatRepository repo = context.getBean(SpringCatRepository.class);
       // repo.init();

        repo.findAll().forEach(System.out::println);
        System.out.println("Hello World!");
        //System.out.println(repo.read(3));
        repo.delete(3);
        repo.findAll().forEach(System.out::println);
        repo.update(1, "Viscas");
        repo.findAll().forEach(System.out::println);
        /*Cat cat = new Cat(1, "Мурло", 4, true);
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
        adCat.findAll();*/
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
