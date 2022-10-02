package com.archive.fursuit.controllers;

import com.archive.fursuit.Photo;
import com.archive.fursuit.repositories.EventRepository;
import com.archive.fursuit.services.EventService;
import com.archive.fursuit.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private EventService eventService;

    @PostMapping("photos")
    public String savePhoto(@ModelAttribute ("photo") Photo photo){
        photoService.savePhoto(photo);
        return "redirect:/";
    }

    @GetMapping("photo/assign")
    public String findPhotos(Model model){
        model.addAttribute("photos", photoService.findAllPhotosWithoutEvent());
        return "unassignedPhotos";
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
        newPhoto.setLabel(photo.getLabel());
        newPhoto.setPhotographer(photo.getPhotographer());
        photoService.assignEvent(newPhoto, id);
        return "redirect:/";
    }
}
