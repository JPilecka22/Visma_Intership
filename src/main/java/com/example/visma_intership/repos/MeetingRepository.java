package com.example.visma_intership.repos;

import com.example.visma_intership.entities.Category;
import com.example.visma_intership.entities.Meeting;
import com.example.visma_intership.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
    Meeting findMeetingByDescriptionContaining(String string);

    Meeting findMeetingByResponsiblePerson(Person person);

    Meeting findMeetingByCategory(Category category);

    Meeting findMeetingByType(com.example.visma_intership.entities.Type type);

    Meeting findMeetingByStartDateStartingWithAndEndDateEndingWith(LocalDateTime startDate, LocalDateTime endDate);
}
