package org.example.repository;

import org.example.model.Cat;

public interface CatRepository extends BaseRepository<Cat, Integer> {
    // можно накиддачть че то что бы расширить
    // конкретный тип который уже у нас будет
    //GUID java.util.UUID.randomUUID();
}
