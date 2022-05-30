package com.example.visma_intership.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Person {
    @Id
    private int id;
    @OneToMany(mappedBy = "responsiblePerson", cascade = CascadeType.ALL)
    private List<Meeting> myManagedMeetings;
    private String name;

    public Person(String name) {
        this.name = name;
    }
}

