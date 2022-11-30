package org.example;

import static org.junit.Assert.assertTrue;

import org.example.model.Cat;
import org.example.repository.CatRepository;
import org.example.repository.SimpleCatRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CatRepositoryTest {
    private CatRepository repository;

    @Before
    public void init() {
        repository = new SimpleCatRepository();
    }

    @Test
    public void shouldAnswerWithTrue() {
        Cat cat1 = new Cat(1,"Mursik", 12,true);
        Cat cat2 = new Cat(2,"Murka", 5,false);
        Cat cat3 = new Cat(3,"Barsik", 0,true);
        Cat cat4 = new Cat(4,"Pufok", 7,false);
        Cat cat5 = new Cat(5,"Shipa", 45,true);

        repository.create(cat1);
        repository.create(cat2);
        repository.create(cat3);
        repository.create(cat4);
        repository.create(cat5);

        List<Cat> cats = repository.findAll();

        Cat newCat1 = new Cat(5,"Shipa", 15,false);
        Cat newCat1 = new Cat(3,"Barsik", 10,false);

        assertTrue(true);
    }
}
