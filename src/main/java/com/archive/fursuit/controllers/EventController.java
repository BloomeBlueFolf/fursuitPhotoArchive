package com.archive.fursuit.controllers;

import com.archive.fursuit.Event;
import com.archive.fursuit.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/event/rename/{id}")
    public String renameEvent(@PathVariable ("id") long id, Model model){
        Event event = new Event();
        model.addAttribute(event);
        model.addAttribute(id);
        return "renameEvent";
    }

    @PostMapping("/event/rename/{id}")
    public String renameEvent(@ModelAttribute Event event, @PathVariable ("id") long id){
        Event renamedEvent = eventService.getEventById(id);
        renamedEvent.setLabel(event.getLabel());
        eventService.saveEvent(renamedEvent);
        return "redirect:/";
    }
}
