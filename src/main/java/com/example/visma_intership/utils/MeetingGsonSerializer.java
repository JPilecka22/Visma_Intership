package com.example.visma_intership.utils;

import com.example.visma_intership.entities.Meeting;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class MeetingGsonSerializer implements JsonSerializer<Meeting> {
    @Override
    public JsonElement serialize(Meeting meeting, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject meetingJson = new JsonObject();
        meetingJson.addProperty("id", meeting.getId());
        meetingJson.addProperty("responsiblePerson", meeting.getResponsiblePerson().toString());
        meetingJson.addProperty("description", meeting.getDescription());
        meetingJson.addProperty("category", meeting.getCategory().toString());
        meetingJson.addProperty("type", meeting.getType().toString());
        meetingJson.addProperty("startDate", meeting.getStartDate().toString());
        meetingJson.addProperty("endDate", meeting.getEndDate().toString());
        meetingJson.addProperty("peopleInMeeting", meeting.getPeopleInMeeting().toString());
        return meetingJson;
    }
}
