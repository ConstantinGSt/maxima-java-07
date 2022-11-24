package org.example;

import static org.junit.Assert.assertTrue;

import org.example.repository.CatRepository;
import org.example.repository.SimpleCatRepository;
import org.junit.Before;
import org.junit.Test;

/

public class CatRepositoryTest {
    private CatRepository repository;

    @Before
    public void init() {
        repository = new SimpleCatRepository();
    }

    @Test
    public void shouldAnswerWithTrue() {

        assertTrue(true);
    }
}
