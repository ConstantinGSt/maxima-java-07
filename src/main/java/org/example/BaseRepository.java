package org.example;

public interface BaseRepository<T> {

    public void Create();
    public void Read();
    public void Update();
    public void Delete();
    public void findAll();
    
}
