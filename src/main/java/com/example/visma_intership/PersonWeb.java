package com.example.visma_intership;

import com.example.visma_intership.entities.Person;
import com.example.visma_intership.repos.PersonRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@RestController
public class PersonWeb {
    @Autowired
    private PersonRepository personRepository;

    @PostMapping(value = "persons/addPerson")
    public String addNewPerson(@RequestBody String request) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(request, Properties.class);
        Person person = new Person(properties.getProperty("name"));
        personRepository.save(person);
        if (personRepository.findById(person.getId()).isPresent()) {
            return "Person successfully created";
        } else return "Person not created";
    }
}
