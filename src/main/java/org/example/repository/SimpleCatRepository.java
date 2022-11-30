package org.example.repository;

import org.example.model.Cat;

import java.util.List;

public class SimpleCatRepository implements CatRepository {

    @Override
    public boolean Create(Cat value) {
        return false;
    }

    @Override
    public Cat Read(Long id) {
        return null;
    }

    @Override
    public int Update(Long id, Cat value) {
        return 0;
    }

    @Override
    public void Delete(Long id) {

    }

    @Override
    public List<Cat> findAll() {
        return null;
    }
}
