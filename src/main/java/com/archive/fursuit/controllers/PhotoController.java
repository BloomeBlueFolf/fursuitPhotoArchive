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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class PhotoController {


//    @InitBinder
//    private void dateBinder(WebDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
//        binder.registerCustomEditor(Date.class, editor);
//    }


    @Autowired
    private PhotoServiceImpl photoServiceImpl;

    @Autowired
    private EventServiceImpl eventServiceImpl;

    @PostMapping("photos")
    public String savePhoto(@ModelAttribute ("photo") Photo photo){
        photoServiceImpl.savePhoto(photo);
        return "redirect:/";
    }

    @GetMapping("photo/assign")
    public String findPhotos(Model model){
        model.addAttribute("photos", photoServiceImpl.findAllPhotosWithoutEvent());
        return "unassignedPhotos";
    }

    @GetMapping("/photo/move")
    public String movePhoto(Model model, @RequestParam long eventId, @RequestParam long photoId){
        Photo movedPhoto = photoServiceImpl.getPhotoById(photoId);
        List<Event> events = eventServiceImpl.getAllEvents();
        model.addAttribute("photo", movedPhoto);
        model.addAttribute("eventId", eventId);
        model.addAttribute("photoId", photoId);
        model.addAttribute("events", events);
        return "MovePhoto";
    }

    @PostMapping("/photo/move")
    public ModelAndView movePhoto(@ModelAttribute ("photo") Photo photo, RedirectAttributes redirectAttributes, @RequestParam long eventId, @RequestParam long photoId){
        Photo movedPhoto = photoServiceImpl.getPhotoById(photoId);
        movedPhoto.setEvent(photo.getEvent());
        photoServiceImpl.savePhoto(movedPhoto);
        redirectAttributes.addAttribute("id", eventId);
        return new ModelAndView("redirect:/event/showPhotos");
    }

    @GetMapping("/photo/upload")
    public String uploadPhoto(Model model, @RequestParam long id){
        Photo photo = new Photo();
        model.addAttribute("photo", photo);
        model.addAttribute("id", id);
        return "uploadPhoto";
    }

    @PostMapping("/photo/upload")
    public String uploadPhoto(@ModelAttribute ("photo") Photo photo, @RequestParam long id){
        Photo newPhoto = new Photo();
        newPhoto.setImage(photo.getImage());
        newPhoto.setLabel(photo.getLabel());
        newPhoto.setPhotographer(photo.getPhotographer());
        newPhoto.setDate(photo.getDate());
        photoServiceImpl.assignEvent(newPhoto, id);
        return "redirect:/";
    }
}
