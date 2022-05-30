package com.example.visma_intership;

import com.example.visma_intership.entities.Category;
import com.example.visma_intership.entities.Meeting;
import com.example.visma_intership.entities.Person;
import com.example.visma_intership.entities.Type;
import com.example.visma_intership.errors.PersonNotFound;
import com.example.visma_intership.repos.MeetingRepository;
import com.example.visma_intership.repos.PersonRepository;
import com.example.visma_intership.utils.LocalDateTimeGsonSerializer;
import com.example.visma_intership.utils.MeetingGsonSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Properties;

@RestController
public class MeetingWeb {
    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private PersonRepository personRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @PostMapping(value = "meetings/addMeeting")
    public String addNewMeeting(@RequestBody String request) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(request, Properties.class);
        Meeting meeting = new Meeting(properties.getProperty("name"),
                properties.getProperty("description"),
                Category.valueOf(properties.getProperty("category")),
                Type.valueOf(properties.getProperty("type")),
                LocalDateTime.parse(properties.getProperty("startDate"), formatter),
                LocalDateTime.parse(properties.getProperty("endDate"), formatter));
        meetingRepository.save(meeting);
        if (meetingRepository.findById(meeting.getId()).isPresent()) {
            return "Meeting successfully created";
        } else return "Meeting not created";
    }

    @GetMapping(value = "meetings/getAll")
    public @ResponseBody
    Iterable<Meeting> getAll() {
        return meetingRepository.findAll();
    }

    @GetMapping(value = "meetings/getByDescription")
    public @ResponseBody
    Meeting getByDescription(@RequestParam String request) {
        return meetingRepository.findMeetingByDescriptionContaining(request);
    }

    @GetMapping(value = "meetings/getByResponsiblePerson/{id}")
    public @ResponseBody
    Meeting getByResponsiblePerson(@PathVariable int id) {
        Person person = personRepository.getById(id);
        return meetingRepository.findMeetingByResponsiblePerson(person);
    }

    @GetMapping(value = "meetings/getByCategory")
    public @ResponseBody
    Meeting getByCategory(@RequestParam String category) {
        return meetingRepository.findMeetingByCategory(Category.valueOf(category));
    }

    @GetMapping(value = "meetings/getByType")
    public @ResponseBody
    Meeting getByType(@RequestParam String type) {
        return meetingRepository.findMeetingByType(Type.valueOf(type));
    }

    @GetMapping(value = "meetings/getByDates")
    public @ResponseBody
    Meeting getByDates(@RequestParam String startDate, String endDate) {
        return meetingRepository.findMeetingByStartDateStartingWithAndEndDateEndingWith(LocalDateTime.parse(startDate), LocalDateTime.parse(endDate));
    }

    @GetMapping(value = "meetings/getByNumberOfAttendees")
    public @ResponseBody
    Meeting getByNumOfAttendees(@RequestParam String startDate, String endDate) {
        return meetingRepository.findMeetingByStartDateStartingWithAndEndDateEndingWith(LocalDateTime.parse(startDate), LocalDateTime.parse(endDate));
    }

    //in the path, id of the meeting
    @PutMapping(value = "meetings/addPerson/{id}")
    public @ResponseBody
    String addPerson(@RequestParam String personName, @RequestParam String personId, @PathVariable int id) {
        Person person = personRepository.findPersonByNameAndId(personName, Integer.valueOf(personId));
        Meeting meeting = meetingRepository.getById(id);
        if (!meeting.getPeopleInMeeting().containsKey(person.getId())) {
            meeting.addPerson(person.getId());
        } else {
            return "Person Already in the meeting";
        }
        meetingRepository.save(meeting);
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeGsonSerializer()).registerTypeAdapter(Meeting.class, new MeetingGsonSerializer());
        return gson.create().toJson(meeting.getPeopleInMeeting());
    }

    //in the path, id of a meeting
    @PutMapping(value = "meetings/removePerson/{id}")
    public @ResponseBody
    String removePerson(@RequestParam String personName, @RequestParam String personId, @PathVariable int id) {
        Person person = personRepository.findPersonByNameAndId(personName, Integer.valueOf(personId));
        if (person == null) {
            throw new PersonNotFound(Integer.parseInt(personId));
        }
        Meeting meeting = meetingRepository.getById(id);
        if (person.getMyManagedMeetings().contains(meeting)) {
            return "Could not delete responsible person from a meeting";
        } else {
            meeting.removePerson(person);
            meetingRepository.save(meeting);
        }
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeGsonSerializer()).registerTypeAdapter(Meeting.class, new MeetingGsonSerializer());
        return gson.create().toJson(meeting.getPeopleInMeeting());
    }


}
