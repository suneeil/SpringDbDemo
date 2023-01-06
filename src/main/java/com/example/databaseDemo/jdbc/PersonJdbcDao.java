package com.example.databaseDemo.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PersonJdbcDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    // In case we need to map a different type of data from DB to Person then we can use the below class,  then we
    //do not need to use new BeanPropertyRowMapper<>(Person.class) in functions like findAll, findById, deleteById,
    //deleteByIdAndName, deleteByIdAndName, insertPerson, updatePerson
    //and add return jdbcTemplate.query("select * from person", new PersonWrapper());

    class PersonWrapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Person person = new Person();
            person.setId(rs.getInt("id"));
            person.setName(rs.getString("name"));
            person.setLocation(rs.getString("location"));
            return person;
        }
    }

    //Select * from 'person'
    public List<Person> findAll() {
        return jdbcTemplate.query("select * from person", new PersonWrapper());
        //mapping the database row to new BeanPropertyRowMapper<>(Person.class)
        //If we are using a BeanProperty row mapper the "Person" class should have a "no argument constructor"
    }

    public Person findById(int id) {
        return jdbcTemplate.queryForObject("select * from person where id=?",
                 new Object[] {id}, new BeanPropertyRowMapper<>(Person.class));
        //If we are using a BeanProperty row mapper the "Person" class should have a "no argument constructor"
    }


    public int deleteById(int id) {
        return jdbcTemplate.update("delete from person where id=?", new Object[]{id});
    }

    public int deleteByIdAndName(int id, String name) {
        return jdbcTemplate.update("delete from person where id=? and name=?", new Object[]{id, name});
    }

    public int insertPerson(Person person) {
        System.out.println(person.getId() + " -- " + person.getName() + " -- " + person.getLocation() );
        return jdbcTemplate.update("insert into person (id, name, location) values(?,?,?)",
                new Object[]{person.getId(), person.getName(), person.getLocation()});
    }

    public int updatePerson(Person person) {
        return jdbcTemplate.update("update person set name = ?, location = ? where id = ?",
                new Object[]{person.getName(), person.getLocation(), person.getId()});
    }

}
