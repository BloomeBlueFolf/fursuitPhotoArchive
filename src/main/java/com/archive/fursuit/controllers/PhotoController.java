package com.archive.fursuit.controllers;

import com.archive.fursuit.Event;
import com.archive.fursuit.Photo;
import com.archive.fursuit.services.EventService;
import com.archive.fursuit.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private EventService eventService;

    @GetMapping("/photo/upload/{id}")
    public String uploadPhoto(Model model, @PathVariable ("id") int id){
        Photo photo = new Photo();
        model.addAttribute("photo", photo);
        model.addAttribute("event", eventService.getEventById(id));
        return "UploadPhoto";
    }
}
