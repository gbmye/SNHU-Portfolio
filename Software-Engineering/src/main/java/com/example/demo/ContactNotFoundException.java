package com.example.demo;

class ContactNotFoundException extends RuntimeException {

    ContactNotFoundException(Long id) {
        super("Could not find employee " + id);
    }
}