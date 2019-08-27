package com.smartshop.beugro;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class Dao {
    private JdbcTemplate jdbcTemplate;

    public Dao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void saveUser(User user){
        jdbcTemplate.update("Insert into users (name,password) values(?,?)",user.getName(),user.getPassword());
    }
    public List<String> listUserNames(){
       return jdbcTemplate.query("Select name, password from users",
               (rs,i) -> rs.getString("name"));
    }
    public User getUserByName(String name){
        return jdbcTemplate.queryForObject("Select name, password from users where name = ?",
                (rs,i)->new User(rs.getString("name"),rs.getString("password")),
                name);
    }
}
