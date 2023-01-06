package com.example.databaseDemo;

import com.example.databaseDemo.jdbc.Person;
import com.example.databaseDemo.jdbc.PersonJdbcDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class DatabaseDemoApplication implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	PersonJdbcDao dao;
	public static void main(String[] args) {
		SpringApplication.run(DatabaseDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Person> all = dao.findAll();
		logger.info("All users -> {}", all);
		logger.info("User Id 10002 -> {}", dao.findById(10002));
		logger.info("Deleting  Id 10001, number of rows deleted -> {}", dao.deleteById(10001));
		logger.info("Deleting  Id 10005 and Rat, number of rows deleted -> {}", dao.deleteByIdAndName(10005, "Rat"));
		logger.info("Insert  new Id 10007 and Sai, number of rows Inserted -> {}", dao.insertPerson(new Person(10007, "Sai", "Shirdi")));
		logger.info("Update Id 10002 location Updated-> {}", dao.updatePerson(new Person(10002, "Paul", "Dhaka")));
	}
}
