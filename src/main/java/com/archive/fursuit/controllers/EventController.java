package com.archive.fursuit.controllers;

import com.archive.fursuit.Event;
import com.archive.fursuit.Photo;
import com.archive.fursuit.services.impl.EventServiceImpl;
import com.archive.fursuit.services.impl.PhotoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EventController {

    Logger logger = LoggerFactory.getLogger("EventController");

    @Autowired
    private EventServiceImpl eventServiceImpl;

    @Autowired
    private PhotoServiceImpl photoServiceImpl;

    @GetMapping("/user")
    public String getEvents(Model model){

        logger.info("Get all events ordered evoked.");

        model.addAttribute("events", eventServiceImpl.showEventsOrdered());
        logger.info("Index page evoked.");
        return "index";
    }

    @GetMapping("/admin/event/add")
    public String createEvent(Model model){

        logger.info("Add event evoked. GET");

        Event event = new Event();
        model.addAttribute("event", event);
        logger.info("Event creation form page evoked.");
        return "createEvent";
    }

    @PostMapping("/admin/events")
    public String saveEvent(@ModelAttribute ("event") Event event){

        logger.info("Add event evoked. POST");

        eventServiceImpl.saveEvent(event);
        logger.info("Event saved in database. Redirect to event creation form page.");
        return "redirect:/user?eventcreated";
    }

    @GetMapping("/user/event/showPhotos")
    public String showPhotos(Model model, @RequestParam long id){

        logger.info("Show all photographies of event evoked.");

        Event event = eventServiceImpl.getEventById(id);
        model.addAttribute("photos", eventServiceImpl.sortPhotosByIdDesc(event));
        model.addAttribute("event", eventServiceImpl.getEventById(id));
        logger.info("Photos of event page evoked.");
        return "PhotosOfEvent";
    }

    @GetMapping("/admin/event/deletePhoto/warning")
    public String deletePhotoWarning(Model model, @RequestParam long photoId,
                                     @RequestParam long eventId){

        logger.info("Photography deletion warning page evoked.");

        model.addAttribute("eventId", eventId);
        model.addAttribute("photoId", photoId);
        return "deleteWarningPhoto";
    }

    @GetMapping("/admin/event/deletePhoto")
    public String deletePhoto(@RequestParam long photoId,
                              @RequestParam long eventId,
                              RedirectAttributes redirectAttributes){

        logger.info("Photography deletion deletion evoked.");

        eventServiceImpl.deletePhoto(photoId);
        redirectAttributes.addAttribute("id", eventId);
        logger.info("Photography deleted. Redirect to show photograpies page.");
        return "redirect:/user/event/showPhotos?photodeleted";
    }

    @GetMapping("/admin/event/editPhoto")
    public String editPhoto(Model model,
                            @RequestParam long photoId,
                            @RequestParam long eventId){

        logger.info("Photography edit evoked. GET");

        Photo editedPhoto = photoServiceImpl.getPhotoById(photoId);
        model.addAttribute("photo", editedPhoto);
        model.addAttribute("eventId", eventId);
        logger.info("Photography edit form page evoked.");
        return "editPhoto";
    }

    @PostMapping("/admin/event/editPhoto")
    public String editPhoto(RedirectAttributes redirectAttributes,
                            @RequestParam long photoId,
                            @RequestParam long eventId,
                            @ModelAttribute Photo photo){

        logger.info("Photography edit evoked. POST");

        Photo editedPhoto = photoServiceImpl.getPhotoById(photoId);
        editedPhoto.setLabel(photo.getLabel());
        editedPhoto.setPhotographer(photo.getPhotographer());
        editedPhoto.setDate(photo.getDate());
        photoServiceImpl.savePhoto(editedPhoto);
        redirectAttributes.addAttribute("id", eventId);
        logger.info("Photography edited. Redirect to show photographies page.");
        return "redirect:/user/event/showPhotos?photoedited";
    }

    @GetMapping("/admin/event/delete/warning")
    public String deleteWarning(Model model,
                                @RequestParam long id){

        logger.info("Event deletion evoked.");

        model.addAttribute("id", id);
        logger.info("Event deletion warning page evoked.");
        return "deleteWarning";
    }

    @GetMapping("/admin/event/delete")
    public String deleteEvent(@RequestParam long id){

        eventServiceImpl.deleteEvent(id);
        logger.info("Event deleted. Redirect to index page.");
        return "redirect:/user?eventdeleted";
    }

    @GetMapping("/admin/event/rename")
    public String renameEvent(@RequestParam long id,
                              Model model){

        logger.info("Event rename evoked.");

        Event event = eventServiceImpl.getEventById(id);
        model.addAttribute("event", event);
        model.addAttribute("id", id);
        logger.info("Event rename form page evoked.");
        return "renameEvent";
    }

    @PostMapping("/admin/event/rename")
    public String renameEvent(@ModelAttribute Event event,
                              @RequestParam long id){

        eventServiceImpl.renameEvent(id, event);
        logger.info("Event renamed. Redirect to index page.");
        return "redirect:/user?eventrenamed";
    }
}
