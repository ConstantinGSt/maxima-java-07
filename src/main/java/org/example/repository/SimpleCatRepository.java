package org.example.repository;

import org.example.model.Cat;
import java.util.List;

public class SimpleCatRepository implements CatRepository {

    @Override
    public boolean create(Cat value) {
        return false;
    }

    @Override
    public Cat read(Long id) {
        return null;
    }

    @Override
    public int update(Long id, Cat value) {
        return 0;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Cat> findAll() {
        return null;
    }
}
