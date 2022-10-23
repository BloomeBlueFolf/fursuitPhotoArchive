package com.archive.fursuit.controllers;

import com.archive.fursuit.Event;
import com.archive.fursuit.services.impl.EventServiceImpl;
import com.archive.fursuit.services.impl.PhotoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/events")
public class EventREST {

    @Autowired
    private EventServiceImpl eventServiceImpl;

    @Autowired
    private PhotoServiceImpl photoServiceImpl;

    @PostMapping("add")
    public Event addEvent(@RequestBody Event event){
        eventServiceImpl.saveEvent(event);
        return event;
    }

    @GetMapping("find")
    public List<Event> findEvents(){
        return eventServiceImpl.showEventsOrdered();
    }

    @PutMapping("update/{id}/{label}")
    public Event updateEvent(@PathVariable ("id") long id, @PathVariable ("label") String label){
        Event updatedEvent = eventServiceImpl.getEventById(id);
        updatedEvent.setLabel(label);
        eventServiceImpl.saveEvent(updatedEvent);
        return  updatedEvent;
    }
}
