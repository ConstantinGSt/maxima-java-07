package org.example.repository;

import java.util.List;

public interface BaseRepository<T, D> {
    //CRUD
    boolean create(T value); // создание save() или вернуть id если бд создает их сама
    T read(D id);     //findById
    int update(D id, T value); // save() хм
    void delete(D id);  // remove
    //Search
    List<T> findAll();  // search / get(); SELECT а надо ли возвращать все! =)

}
