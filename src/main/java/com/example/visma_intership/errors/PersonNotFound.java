package com.example.visma_intership.errors;

public class PersonNotFound extends RuntimeException {
    public PersonNotFound(Integer id) {
        super("Could not find person with id: " + id);
    }

}
