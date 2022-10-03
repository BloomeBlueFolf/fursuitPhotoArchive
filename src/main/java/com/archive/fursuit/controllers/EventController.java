package com.archive.fursuit.controllers;

import com.archive.fursuit.Event;
import com.archive.fursuit.services.impl.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class EventController {

    @Autowired
    private EventServiceImpl eventServiceImpl;

    @GetMapping("/")
    public String getEvents(Model model){
        model.addAttribute("events", eventServiceImpl.showEvents());
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
        eventServiceImpl.saveEvent(event);
        return "redirect:/";
    }

    @GetMapping("event/showPhotos")
    public String showPhotos(Model model, @RequestParam long id){
        Event event = eventServiceImpl.getEventById(id);
        model.addAttribute("photos", event.getPhotos());
        model.addAttribute("event", eventServiceImpl.getEventById(id));
        return "PhotosOfEvent";
    }

    @GetMapping("event/deletePhoto")
    public String deletePhoto(@RequestParam long photoId, @RequestParam long event_id, RedirectAttributes redirectAttributes){
        //Event event = eventServiceImpl.getEventById(event_id);
        eventServiceImpl.deletePhoto(photoId);
        redirectAttributes.addAttribute("id", event_id);
        return "redirect:/event/showPhotos";
    }

    @GetMapping("/event/delete/warning")
    public String deleteWarning(Model model, @RequestParam long id){
        model.addAttribute("id", id);
        return "deleteWarning";
    }

    @GetMapping("/event/delete")
    public String deleteEvent(@RequestParam long id){
        eventServiceImpl.deleteEvent(id);
        return "redirect:/";
    }

    @GetMapping("/event/rename")
    public String renameEvent(@RequestParam long id, Model model){
        Event event = eventServiceImpl.getEventById(id);
        model.addAttribute("event", event);
        model.addAttribute("id", id);
        return "renameEvent";
    }

    @PostMapping("/event/rename")
    public String renameEvent(@ModelAttribute Event event, @RequestParam long id){
          eventServiceImpl.renameEvent(id, event);
        return "redirect:/";
    }
}
