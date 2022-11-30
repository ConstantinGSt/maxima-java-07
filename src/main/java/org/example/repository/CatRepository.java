package org.example.repository;

import org.example.model.Cat;

public interface CatRepository extends BaseRepository<Cat, Long>{
    // можно накиддачть че то что бы расширить
    // конкретный тип который уже у нас будет
}
