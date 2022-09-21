package com.archive.fursuit.controllers;

import com.archive.fursuit.Event;
import com.archive.fursuit.repositories.EventRepository;
import com.archive.fursuit.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/")
    public String getEvents(Model model){
        model.addAttribute("events", eventService.findAllEvents());
        return "index";
    }
}
