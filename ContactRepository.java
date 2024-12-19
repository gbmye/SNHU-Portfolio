package com.example.relational_data_access;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactRepository {

    private static final Logger log = LoggerFactory.getLogger(ContactRepository.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createTable() {
        log.info("Creating tables");

        jdbcTemplate.execute("DROP TABLE contacts IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE contacts(" +
                "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");
    }

    public int createContact(Contact contact) {

        String createQuery = "INSERT INTO contacts(id," +
                " first_name, last_name) VALUES (?,?,?)";
        return jdbcTemplate.update(createQuery,
                contact.getId(),
                contact.getFirstName(),
                contact.getLastName());
    }

    public List<Contact> readAll() {
        String readAllQuery = "SELECT * FROM contacts";
        return jdbcTemplate.query(readAllQuery,
                new BeanPropertyRowMapper<>(Contact.class));
    }

    public Contact readByID(long id) {
        String readUniqueQuery = "SELECT * FROM contacts WHERE id = ?";
        return jdbcTemplate.queryForObject(readUniqueQuery,
                BeanPropertyRowMapper.newInstance(Contact.class), id);
    }

    public int update(Contact contact) {
        String updateQuery = "UPDATE contact SET" +
                "id = ?, first_name = ?, last_name = ? WHERE id = ?";
        return jdbcTemplate.update(updateQuery, contact.getId(), contact.getFirstName(),
                contact.getLastName(), contact.getId());
    }

    public int delete(long id) {
        String deleteQuery = "DELETE FROM contacts WHERE id = ?";
        return jdbcTemplate.update(deleteQuery, id);
    }

}
