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

    @GetMapping("/event/delete")
    public String deleteEvent(@RequestParam long id){
        eventService.deleteEvent(id);
        return "redirect:/";
    }

    @GetMapping("/event/rename")
    public String renameEvent(@RequestParam long id, Model model){
        Event event = eventService.getEventById(id);
        model.addAttribute(event);
        model.addAttribute(id);
        return "renameEvent";
    }

    @PostMapping("/event/rename")
    public String renameEvent(@ModelAttribute Event event, @RequestParam long id){
          eventService.renameEvent(id, event);
        return "redirect:/";
    }
}
