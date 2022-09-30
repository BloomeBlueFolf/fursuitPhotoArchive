package com.archive.fursuit.controllers;

import com.archive.fursuit.Event;
import com.archive.fursuit.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/")
    public String getEvents(Model model){
        model.addAttribute("events", eventService.showEvents());
        return "index";
    }

    @GetMapping("/event/add")
    public String createEvent(Model model){
        Event event = new Event();
        model.addAttribute("event", event);
        return "createEvent";
    }

    @PostMapping("/events")
    public String saveEvent(@ModelAttribute ("event") Event event){
        eventService.saveEvent(event);
        return "redirect:/";
    }

    @GetMapping("/event/delete/{id}")
    public String deleteEvent(@PathVariable long id){
        eventService.deleteEvent(id);
        return "redirect:/";
    }
}
