package com.archive.fursuit.controllers;

import com.archive.fursuit.Event;
import com.archive.fursuit.services.EventService;
import com.archive.fursuit.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class EventREST {

    @Autowired
    private EventService eventService;

    @Autowired
    private PhotoService photoService;

    @PostMapping("event/add")
    public Event addEvent(@RequestBody Event event){
        eventService.saveEvent(event);
        return event;
    }

    @GetMapping("events/find")
    public List<Event> findEvents(){
        return eventService.showEvents();
    }

    @PutMapping("event/update/{id}/{label}")
    public Event updateEvent(@PathVariable ("id") long id, @PathVariable ("label") String label){
        Event updatedEvent = eventService.getEventById(id);
        updatedEvent.setLabel(label);
        eventService.saveEvent(updatedEvent);
        return  updatedEvent;
    }
}
