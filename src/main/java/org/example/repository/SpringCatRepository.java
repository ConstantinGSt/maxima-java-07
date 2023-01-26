package org.example.repository;

import org.example.model.Cat;
import org.springframework.beans.factory.annotation.Autowired;
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

    private JdbcTemplate jdbcTemplate;

    public void init() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS cats (id INT, Name VARCHAR(50)," +
                " Weight INT, isAngry boolean)");  //query , update

    }


    @Override
    public boolean create(Cat value) {
        return false;
    }

    @Override
    public Cat read(Integer id) {
        return null;
    }

    @Override
    public int update(Integer id, Cat value) {
        return 0;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Cat> findAll() {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM cats", catRowMapper));
    }
}
