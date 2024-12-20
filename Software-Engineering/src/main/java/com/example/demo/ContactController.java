package com.example.demo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
class ContactController {

    private final ContactRepository repository;

    private final ContactModelAssembler assembler;

    ContactController(ContactRepository repository, ContactModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }


    @GetMapping("/contacts")
    CollectionModel<EntityModel<Contact>> all() {

        List<EntityModel<Contact>> employees = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(employees, linkTo(methodOn(ContactController.class).all()).withSelfRel());
    }

    @PostMapping("/contacts")
    ResponseEntity<?> newEmployee(@RequestBody Contact newContact) {

        EntityModel<Contact> entityModel = assembler.toModel(repository.save(newContact));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    // Single item

    @GetMapping("/contacts/{id}")
    EntityModel<Contact> one(@PathVariable Long id) {

        Contact contact = repository.findById(id) //
                .orElseThrow(() -> new ContactNotFoundException(id));

        return assembler.toModel(contact);
    }

    @PutMapping("/contacts/{id}")
    ResponseEntity<?> replaceContact(@RequestBody Contact newContact, @PathVariable Long id) {

        Contact updatedContact = repository.findById(id) //
                .map(contact -> {
                    contact.setFirstName(newContact.getFirstName());
                    contact.setLastName(newContact.getLastName());
                    contact.setNumber(newContact.getNumber());
                    contact.setAddress(newContact.getAddress());
                    return repository.save(contact);
                }) //
                .orElseGet(() -> {
                    return repository.save(newContact);
                });

        EntityModel<Contact> entityModel = assembler.toModel(updatedContact);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/contacts/{id}")
    ResponseEntity<?> deleteContact(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
