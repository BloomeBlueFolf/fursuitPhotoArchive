package com.archive.fursuit.controllers;

import com.archive.fursuit.Event;
import com.archive.fursuit.User;
import com.archive.fursuit.services.impl.EventServiceImpl;
import com.archive.fursuit.services.impl.PhotoServiceImpl;
import com.archive.fursuit.services.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger("EventREST");

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
        logger.info("API show all events called.");

        List<Event> events = eventServiceImpl.showEventsOrdered();
        if (events.isEmpty()) {
            logger.error("No existing events.");
            return new ResponseEntity<>("Currently there are no events existing.", HttpStatus.OK);
        } else {
            logger.info("Return list of events.");
            return new ResponseEntity<>(events, HttpStatus.OK);
        }
    }

    @GetMapping("public/events/findLabels")
    public ResponseEntity<List<String>> findEventLabels(){
        logger.info("API show labels of all events called.");

        List<Event> eventList = eventServiceImpl.showEventsOrdered();
        List<String> labelList = new LinkedList<>();
        for (Event event : eventList) {
            labelList.add(event.getLabel());
        }
        logger.info("Return list of all event labels.");
        return new ResponseEntity<>(labelList, HttpStatus.OK);
    }

    @GetMapping("public/event/find/{id}")
    public ResponseEntity<?> find(@PathVariable ("id") long id){
        logger.info("API find event by ID called.");

        Event event = eventServiceImpl.getEventById(id);
        if (event == null) {
            logger.error("Event not found.");
            return new ResponseEntity<>("An event with this ID doesn't exist.", HttpStatus.NOT_FOUND);
        } else {
            logger.info("Return event.");
            return new ResponseEntity<>(event, HttpStatus.OK);
        }
    }

    @PostMapping("private/event/add")
    public ResponseEntity<?> addEvent(@RequestBody Event event,
                                      @RequestHeader Map<String, String> header) {
        logger.info("API add event called.");

        User authUser = userService.findUser(header.get("username"));
        logger.info("Check credentials.");
        if (authUser != null && passwordEncoder.matches(header.get("password"), authUser.getPassword())) {
            logger.info("Credentials ok.");
            eventServiceImpl.saveEvent(event);
            logger.info("Return created event.");
            return new ResponseEntity<>(event, HttpStatus.CREATED);
        } else {
            logger.info("Credentials not ok.");
            return new ResponseEntity<>("Wrong credentials. Try again.", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("private/event/rename/{id}")
    public ResponseEntity<?> renameEvent(@PathVariable ("id") long id,
                                         @RequestBody Event event,
                                         @RequestHeader Map<String, String> header){
        logger.info("API rename event called.");

        User authUser = userService.findUser(header.get("username"));
        logger.info("Check credentials.");
        if (authUser != null && passwordEncoder.matches(header.get("password"), authUser.getPassword())) {
            logger.info("Credentials ok.");
            Event renamedEvent = eventServiceImpl.getEventById(id);
            if(renamedEvent == null){
                logger.error("Event not found.");
                return new ResponseEntity<>(String.format("An event with ID %s doesn't exist.", id), HttpStatus.NOT_FOUND);
            }
            else{
                renamedEvent.setLabel(event.getLabel());
                eventServiceImpl.saveEvent(renamedEvent);
                return new ResponseEntity<>(String.format("Event with ID %s successfully renamed", id), HttpStatus.OK);
            }
        } else {
            logger.info("Credentials not ok.");
            return new ResponseEntity<>("Wrong credentials. Try again.", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("private/event/delete/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable ("id") long id,
                                         @RequestHeader Map<String, String> header){
        logger.info("API delete event called.");

        User authUser = userService.findUser(header.get("username"));
        logger.info("Check credentials.");
        if (authUser != null && passwordEncoder.matches(header.get("password"), authUser.getPassword())) {
            logger.info("Credentials ok.");
            Event deleteEvent = eventServiceImpl.getEventById(id);
            if(deleteEvent == null){
                logger.error("Event not found.");
                return new ResponseEntity<>(String.format("An event with ID %s doesn't exist.", id), HttpStatus.NOT_FOUND);
            }
            else {
                eventServiceImpl.deleteEvent(id);
                return new ResponseEntity<>(String.format("Event with ID %s successfully deleted.", id), HttpStatus.OK);
            }
        } else {
            logger.info("Credentials not ok.");
            return new ResponseEntity<>("Wrong credentials. Try again.", HttpStatus.FORBIDDEN);
        }
    }
}
