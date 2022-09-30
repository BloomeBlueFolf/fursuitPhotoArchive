package com.archive.fursuit.controllers;

import com.archive.fursuit.Event;
import com.archive.fursuit.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class EventREST {

    @Autowired
    private EventService eventService;

    @PostMapping("event/add")
    public Event addEvent(@RequestBody Event event){
        eventService.saveEvent(event);
        return event;
    }

    @GetMapping("event/find")
    public List<Event> findEvents(){
        return eventService.showEvents();
    }

    @PostMapping("event/delete")
    public void deleteEvent(@RequestBody long id){
        eventService.deleteEvent(id);
    }
}
