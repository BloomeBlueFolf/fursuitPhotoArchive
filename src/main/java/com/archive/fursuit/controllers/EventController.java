package com.archive.fursuit.controllers;

import com.archive.fursuit.Event;
import com.archive.fursuit.Photo;
import com.archive.fursuit.services.impl.EventServiceImpl;
import com.archive.fursuit.services.impl.PhotoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.swing.text.DateFormatter;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;


@Controller
public class EventController {


//    @InitBinder
//    private void initBinder(WebDataBinder binder) {
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        binder.addCustomFormatter(dtf, "date");
//    }

    @Autowired
    private EventServiceImpl eventServiceImpl;

    @Autowired
    private PhotoServiceImpl photoServiceImpl;

    @GetMapping("/")
    public String getEvents(Model model){
        model.addAttribute("events", eventServiceImpl.showEventsOrdered());
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

    @GetMapping("event/deletePhoto/warning")
    public String deletePhotoWarning(Model model, @RequestParam long photoId, @RequestParam long eventId){
        model.addAttribute("eventId", eventId);
        model.addAttribute("photoId", photoId);
        return "deleteWarningPhoto";
    }

    @GetMapping("event/deletePhoto")
    public String deletePhoto(@RequestParam long photoId, @RequestParam long eventId, RedirectAttributes redirectAttributes){
        eventServiceImpl.deletePhoto(photoId);
        redirectAttributes.addAttribute("id", eventId);
        return "redirect:showPhotos";
    }

    @GetMapping("/event/editPhoto")
    public String editPhoto(Model model, @RequestParam long photoId, @RequestParam long eventId){
        Photo editedPhoto = photoServiceImpl.getPhotoById(photoId);
        model.addAttribute("photo", editedPhoto);
        model.addAttribute("eventId", eventId);
        return "editPhoto";
    }

    @PostMapping("/event/editPhoto")
    public String editPhoto(RedirectAttributes redirectAttributes, @RequestParam long photoId, @RequestParam long eventId, @ModelAttribute Photo photo){
        Photo editedPhoto = photoServiceImpl.getPhotoById(photoId);
        editedPhoto.setLabel(photo.getLabel());
        editedPhoto.setPhotographer(photo.getPhotographer());
        editedPhoto.setDate(photo.getDate());
        photoServiceImpl.savePhoto(editedPhoto);
        redirectAttributes.addAttribute("id", eventId);
        return "redirect:showPhotos";
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
