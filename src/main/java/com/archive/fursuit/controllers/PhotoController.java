package com.archive.fursuit.controllers;

import com.archive.fursuit.Event;
import com.archive.fursuit.ImageUtils;
import com.archive.fursuit.Photo;
import com.archive.fursuit.services.impl.EventServiceImpl;
import com.archive.fursuit.services.impl.PhotoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PhotoController {

    Logger logger = LoggerFactory.getLogger("PhotoController");

    @Autowired
    private PhotoServiceImpl photoServiceImpl;

    @Autowired
    private EventServiceImpl eventServiceImpl;

    @GetMapping("/admin/photo/move")
    public String movePhoto(Model model, @RequestParam long eventId,
                            @RequestParam long photoId){
        logger.info("Photography move evoked. GET");

        logger.info("Get photography by ID from database.");
        Photo movedPhoto = photoServiceImpl.getPhotoById(photoId);
        logger.info("Get all events from database.");
        List<Event> events = eventServiceImpl.getAllEvents();
        model.addAttribute("photo", movedPhoto);
        model.addAttribute("eventId", eventId);
        model.addAttribute("photoId", photoId);
        model.addAttribute("events", events);
        logger.info("Move photography page evoked.");
        return "MovePhoto";
    }

    @PostMapping("/admin/photo/move")
    public ModelAndView movePhoto(@ModelAttribute ("photo") Photo photo,
                                  RedirectAttributes redirectAttributes,
                                  @RequestParam long eventId,
                                  @RequestParam long photoId){

        logger.info("Photography move evoked. POST");

        Photo movedPhoto = photoServiceImpl.getPhotoById(photoId);
        movedPhoto.setEvent(photo.getEvent());
        logger.info("Save moved photography in database.");
        photoServiceImpl.savePhoto(movedPhoto);
        redirectAttributes.addAttribute("id", eventId);
        logger.info("Redirect to show photographies page.");
        return new ModelAndView("redirect:/user/event/showPhotos?photomoved");
    }

    @GetMapping("/admin/photo/upload")
    public String uploadPhoto(Model model,
                              @RequestParam long id){

        logger.info("Photography upload evoked. GET");

        Photo photo = new Photo();
        model.addAttribute("photo", photo);
        model.addAttribute("id", id);
        logger.info("Photography upload form page evoked.");
        return "uploadPhoto";
    }

    @PostMapping("/admin/photo/upload")
    public ModelAndView uploadPhoto(@ModelAttribute ("photo") Photo photo,
                                    @RequestParam long id,
                                    @RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes){

        logger.info("Photography upload evoked. POST");

        String msg = photoServiceImpl.createNewPhoto(photo, id, file);
        redirectAttributes.addAttribute("id", id);
        if(msg.equals("success")) {
            logger.info("Redirect to show photographies page with success param.");
            return new ModelAndView("redirect:/user/event/showPhotos?photouploaded");
        }
        else {
            logger.info("Redirect to photographies page without success param.");
            return new ModelAndView("redirect:/admin/photo/upload?falseType");
        }
    }

    @GetMapping("/user/photo/image")
    public ResponseEntity<?> getImage(@RequestParam long id){

        logger.info("Photography display method evoked.");
        logger.info("Get photography by ID from database.");

        Photo photo = photoServiceImpl.getPhotoById(id);
        logger.info("Return image data to web page.");
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(photo.getFileType()))
                .body(ImageUtils.decompressImage(photo.getImage()));
    }

    @GetMapping("/user/photo/download")
    public ResponseEntity<?> downloadImage(@RequestParam long photoId){

        logger.info("Photography download evoked.");

        HttpHeaders header = new HttpHeaders();
        Photo photo = photoServiceImpl.getPhotoById(photoId);
        header.add(HttpHeaders.CONTENT_DISPOSITION, photo.getLabel()+photo.getFileType());
        logger.info("Return photography for download.");
        return ResponseEntity.status(HttpStatus.OK)
                .headers(header)
                .contentType(MediaType.valueOf(photo.getFileType()))
                .body(ImageUtils.decompressImage(photo.getImage()));
    }
}
