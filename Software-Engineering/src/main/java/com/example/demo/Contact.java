package com.example.demo;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity
class Contact {

    private @Id
    @GeneratedValue Long id;
    private String firstName;
    private String lastName;
    private String number;
    private String address;

    Contact() {}

    Contact(String firstName, String lastName, String number, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.address = address;
    }

    public Long getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Contact contact))
            return false;
        return Objects.equals(this.id, contact.id)
                && Objects.equals(this.firstName, contact.firstName)
                && Objects.equals(this.lastName, contact.lastName)
                && Objects.equals(this.number, contact.number)
                && Objects.equals(this.address, contact.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.firstName, this.lastName, this.number,
                this.address);
    }

    @Override
    public String toString() {
        return "Contact{"
                + "id=" + this.id
                + ", firstName='" + this.firstName + '\''
                + ", lastName='" + this.lastName + '\''
                + ", number=" + this.number + '\''
                + ", address=" + this.address + '\''
                + '}';
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}