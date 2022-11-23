package org.example;

import java.util.List;

public interface BaseRepository<T> {

    public void Create(T... value);
    public T Read(T... value);
    public void Update(T... value);
    public void Delete(T... value);
    public List<T> findAll();

}
