package com.example.visma_intership.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Meeting {
    @Id
    private int id;
    private String name;
    @ManyToOne
    private Person responsiblePerson;
    private String description;
    private Category category;
    private Type type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ElementCollection
    private Map<Integer, LocalDateTime> peopleInMeeting = Collections.EMPTY_MAP;

    public Meeting(String name, String description, Category category, Type type, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;


    }

    public void addPerson(Integer integer) {
        this.peopleInMeeting.put(integer, LocalDateTime.now());

    }

    public void removePerson(Person person) {
        this.peopleInMeeting.remove(person);

    }
}
