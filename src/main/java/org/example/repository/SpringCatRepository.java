package org.example.repository;

import org.example.model.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SpringCatRepository implements  CatRepository {

    @Autowired private DataSource dataSource;
    @Autowired private RowMapper<Cat> catRowMapper;
    private JdbcTemplate jdbcTemplate;//JdbcTemplate для запросов в SQL

    public void init() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS cats (id INT, Name VARCHAR(50)," +
                " Weight INT, isAngry boolean)");  //query , update
        create(new Cat(1, "Мурло", 4, true));
        create(new Cat(2,"Murka", 5,false));
        create(new Cat(3,"Barsik", 12,true));
        create(new Cat(4,"Karlo", 7,true));
    }


    @Override
    public boolean create(Cat value) {
        return jdbcTemplate.update("INSERT INTO cats (id,Name,Weight,isAngry) VALUES (?, ?, ?, ?)",
                value.getId(),
                value.getName(),
                value.getWeight(),
                value.isAngry()
        )>0;
    }

    @Override
    public Cat read(Integer id) {
        return jdbcTemplate.query("SELECT * FROM cats WHERE id = ?", new BeanPropertyRowMapper<>(Cat.class), id).get(0);
        //BeanPropertyRowMapper<Cat>
    }

    @Override
    public int update(Integer id, Cat value) {
        return 0;
    }

    @Override
    public int update(int id, String value) {
        return jdbcTemplate.update("UPDATE cats SET Name=? WHERE id=?", value, id);
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM cats WHERE id = ?", id);
    }

    @Override
    public List<Cat> findAll() {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM cats", catRowMapper));
    }

}
