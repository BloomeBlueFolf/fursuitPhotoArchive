package com.archive.fursuit.controllers;

import com.archive.fursuit.Event;
import com.archive.fursuit.Photo;
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

    @PostMapping("events/delete")
    public void deleteEvent(@RequestBody long id){
        eventService.deleteEvent(id);
    }

    @GetMapping("event/assign/{event_id}/{photo_id}")
    public Event assignPhoto(@PathVariable ("event_id") long event_id, @PathVariable ("photo_id") long photo_id){
        Event event = eventService.getEventById(event_id);
        Photo photo = photoService.getPhotoById(photo_id);
        photoService.assignEvent(photo, event_id);
        return event;
    }

    @PutMapping("event/update/{id}/{label}")
    public Event updateEvent(@PathVariable ("id") long id, @PathVariable ("label") String label){
        Event updatedEvent = eventService.getEventById(id);
        updatedEvent.setLabel(label);
        eventService.saveEvent(updatedEvent);
        return  updatedEvent;
    }
}
