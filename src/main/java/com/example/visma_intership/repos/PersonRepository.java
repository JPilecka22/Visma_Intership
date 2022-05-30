package com.example.visma_intership.repos;

import com.example.visma_intership.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findPersonByNameAndId(String name, Integer id);

}
