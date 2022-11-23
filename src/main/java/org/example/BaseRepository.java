package org.example;

public interface BaseRepository<T> {

    public void Create(T... value);
    public T Read(T... value);
    public void Update(T... value);
    public void Delete(T... value);
    public T findAll();

}
