package com.archive.fursuit.controllers;

import com.archive.fursuit.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    private EventService eventService;

    @GetMapping("/add/{label}")
    public void addSomething(@PathVariable("label") String label){
        eventService.createEvent(label);
    }
}
