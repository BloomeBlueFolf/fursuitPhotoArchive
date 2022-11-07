package com.archive.fursuit.controllers;

import com.archive.fursuit.Event;
import com.archive.fursuit.Photo;
import com.archive.fursuit.services.impl.EventServiceImpl;
import com.archive.fursuit.services.impl.PhotoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/")
public class EventREST {

    @Autowired
    private EventServiceImpl eventServiceImpl;

    @Autowired
    private PhotoServiceImpl photoServiceImpl;

    @PostMapping("private/event/add")
    public Event addEvent(@RequestBody Event event){
        eventServiceImpl.saveEvent(event);
        return event;
    }

    @GetMapping("public/events/find")
    public List<Event> findEvents(){
        return eventServiceImpl.showEventsOrdered();
    }

    @PutMapping("private/event/rename/{id}/{label}")
    public String renameEvent(@PathVariable ("id") long id, @PathVariable ("label") String label){
        Event renamedEvent = eventServiceImpl.getEventById(id);
        if(renamedEvent == null){
            return String.format("An event with ID %s doesn't exist.", id);
        }
        else{
            renamedEvent.setLabel(label);
            eventServiceImpl.saveEvent(renamedEvent);
            return String.format("Event with ID %s successfully renamed to %s.", id, label);
        }
    }

    @DeleteMapping("private/event/delete/{id}")
    public String deleteEvent(@PathVariable ("id") long id){
        Event deleteEvent = eventServiceImpl.getEventById(id);
        if(deleteEvent == null){
            return String.format("An event with ID %s doesn't exist.", id);
        }
        else {
            eventServiceImpl.deleteEvent(id);
            return String.format("Event with ID %s successfully deleted.", id);
        }
    }
    @GetMapping("public/event/find/{id}")
    public Event find(@PathVariable ("id") long id){
        return eventServiceImpl.getEventById(id);
    }
}
