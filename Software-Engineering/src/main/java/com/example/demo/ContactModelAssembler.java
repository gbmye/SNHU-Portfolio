package com.example.demo;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class ContactModelAssembler implements RepresentationModelAssembler<Contact, EntityModel<Contact>> {

    @Override
    public EntityModel<Contact> toModel(Contact contact) {

        return EntityModel.of(contact, //
                linkTo(methodOn(ContactController.class).one(contact.getId())).withSelfRel(),
                linkTo(methodOn(ContactController.class).all()).withRel("contact"));
    }
}
