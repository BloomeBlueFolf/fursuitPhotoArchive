package com.archive.fursuit.controllers;

import com.archive.fursuit.Event;
import com.archive.fursuit.User;
import com.archive.fursuit.services.impl.EventServiceImpl;
import com.archive.fursuit.services.impl.PhotoServiceImpl;
import com.archive.fursuit.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/api/")
public class EventREST {

    @Autowired
    private EventServiceImpl eventServiceImpl;

    @Autowired
    private PhotoServiceImpl photoServiceImpl;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("public/events/find")
    public ResponseEntity<?> findEvents(){

        List<Event> events = eventServiceImpl.showEventsOrdered();
        if (events.isEmpty()) {
            return new ResponseEntity<>("Currently there are no events existing.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(events, HttpStatus.OK);
        }
    }

    @GetMapping("public/events/findLabels")
    public ResponseEntity<List<String>> findEventLabels(){

        List<Event> eventList = eventServiceImpl.showEventsOrdered();
        List<String> labelList = new LinkedList<>();
        for (Event event : eventList) {
            labelList.add(event.getLabel());
        }
        return new ResponseEntity<>(labelList, HttpStatus.OK);
    }

    @GetMapping("public/event/find/{id}")
    public ResponseEntity<?> find(@PathVariable ("id") long id){

        Event event = eventServiceImpl.getEventById(id);
        if (event == null) {
            return new ResponseEntity<>("An event with this ID doesn't exist.", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(event, HttpStatus.OK);
        }
    }

    @PostMapping("private/event/add")
    public ResponseEntity<?> addEvent(@RequestBody Event event,
                                      @RequestHeader Map<String, String> header) {

        User authUser = userService.findUser(header.get("username"));
        if (authUser != null && passwordEncoder.matches(header.get("password"), authUser.getPassword())) {
            eventServiceImpl.saveEvent(event);
            return new ResponseEntity<>(event, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Wrong credentials. Try again.", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("private/event/rename/{id}")
    public ResponseEntity<?> renameEvent(@PathVariable ("id") long id,
                                         @RequestBody Event event,
                                         @RequestHeader Map<String, String> header){

        User authUser = userService.findUser(header.get("username"));
        if (authUser != null && passwordEncoder.matches(header.get("password"), authUser.getPassword())) {
            Event renamedEvent = eventServiceImpl.getEventById(id);
            if(renamedEvent == null){
                return new ResponseEntity<>(String.format("An event with ID %s doesn't exist.", id), HttpStatus.NOT_FOUND);
            }
            else{
                renamedEvent.setLabel(event.getLabel());
                eventServiceImpl.saveEvent(renamedEvent);
                return new ResponseEntity<>(String.format("Event with ID %s successfully renamed", id), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("Wrong credentials. Try again.", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("private/event/delete/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable ("id") long id,
                                         @RequestHeader Map<String, String> header){

        User authUser = userService.findUser(header.get("username"));
        if (authUser != null && passwordEncoder.matches(header.get("password"), authUser.getPassword())) {
            Event deleteEvent = eventServiceImpl.getEventById(id);
            if(deleteEvent == null){
                return new ResponseEntity<>(String.format("An event with ID %s doesn't exist.", id), HttpStatus.NOT_FOUND);
            }
            else {
                eventServiceImpl.deleteEvent(id);
                return new ResponseEntity<>(String.format("Event with ID %s successfully deleted.", id), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("Wrong credentials. Try again.", HttpStatus.FORBIDDEN);
        }
    }
}
