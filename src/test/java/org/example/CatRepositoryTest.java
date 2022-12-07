package org.example;

import org.example.model.Cat;
import org.example.repository.CatRepository;
import org.example.repository.SimpleCatRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

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
        assertEquals(5, cats.size());

        Cat testCat = repository.read(3);
        assertEquals("Barsik", testCat.getName());
        assertEquals(0, testCat.getWeight());
        assertTrue(testCat.isAngry());

        Cat newCat1 = new Cat(5,"Shipa", 15,false);
        Cat newCat2 = new Cat(3,"BarsUk", 10,false);

        testCat = repository.read(3);
        assertEquals("BarsUk", testCat.getName());
        assertEquals(10, testCat.getWeight());
        assertFalse(testCat.isAngry());

        repository.update(newCat1.getId(), newCat1);
        repository.update(newCat2.getId(), newCat2);

        repository.delete(1);
        testCat = repository.read(1);
        assertNull(testCat);

        repository.delete(4);
        cats = repository.findAll();
        assertEquals(3, cats.size());



        assertTrue(true);
    }
}
