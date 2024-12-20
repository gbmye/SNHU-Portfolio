package com.example.relational_data_access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ContactController {

    private final ContactRepository repository;

    @Autowired
    public ContactController(ContactRepository contactRepository) {
        this.repository = contactRepository;
        this.repository.createTable();
    }

    @PostMapping("/contacts")
    public int createNew(@RequestBody Contact contact) {
        return repository.createContact(contact);
    }

    @GetMapping("/contacts/all")
    public List<Contact> readAll() {
        return repository.readAll();
    }

    @GetMapping("/contacts/{id}")
    public Contact readUnique(@PathVariable("id") long id) {
        return repository.readByID(id);
    }

    @PutMapping("/contacts")
    public int update(@RequestBody Contact contact) {
        return repository.update(contact);
    }

    @DeleteMapping("/contacts/{id}")
    public int delete(@PathVariable("id") long id) {
        return repository.delete(id);
    }
}
