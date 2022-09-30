package com.archive.fursuit.controllers;

import com.archive.fursuit.Photo;
import com.archive.fursuit.services.EventService;
import com.archive.fursuit.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private EventService eventService;

    @GetMapping("photo/upload")
    public String uploadPhoto(Model model){
        Photo photo = new Photo();
        model.addAttribute("photo", photo);
        return "uploadPhoto2";
    }

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

    @GetMapping("/photo/upload/{id}")
    public String uploadPhoto(Model model, @PathVariable long id){
        Photo photo = new Photo();
        model.addAttribute("photo", photo);
        model.addAttribute("id", id);
        return "uploadPhoto";
    }

    @PostMapping("/photo/upload/{id}")
        public String uploadPhoto(@ModelAttribute ("photo") Photo photo, @PathVariable long id){
        photoService.assignEvent(photo, id);
        return "redirect:/";
    }
}
