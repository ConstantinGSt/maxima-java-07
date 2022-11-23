package org.example.repository;

import java.util.List;

public interface BaseRepository<T, D> {
    //CRUD
    boolean Create(T value); // создание save()
    T Read(D id);     //findById
    int Update(D id, T value); // save() хм
    void Delete(D id);  // remove
    //Search
    List<T> findAll();  // search / get(); SELECT

}
