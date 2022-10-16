package com.archive.fursuit.controllers;

import com.archive.fursuit.Event;
import com.archive.fursuit.ImageUtils;
import com.archive.fursuit.Photo;
import com.archive.fursuit.services.impl.EventServiceImpl;
import com.archive.fursuit.services.impl.PhotoServiceImpl;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
    public ModelAndView movePhoto(@ModelAttribute ("photo") Photo photo, RedirectAttributes redirectAttributes,
                                  @RequestParam long eventId, @RequestParam long photoId){
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
    public ModelAndView uploadPhoto(@ModelAttribute ("photo") Photo photo, @RequestParam long id,
                                    @RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes){
        photoServiceImpl.createNewPhoto(photo, id, file);
        redirectAttributes.addAttribute("id", id);
        return new ModelAndView("redirect:/event/showPhotos");
    }

    @GetMapping("/photo/image")
    public ResponseEntity<?> getImage(@RequestParam long id){
        Photo photo = photoServiceImpl.getPhotoById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(photo.getFileType()))
                .body(ImageUtils.decompressImage(photo.getImage()));
    }

    @GetMapping("/photo/download")
    public ResponseEntity<?> downloadImage(@RequestParam long photoId){
        HttpHeaders header = new HttpHeaders();
        Photo photo = photoServiceImpl.getPhotoById(photoId);
        header.add(HttpHeaders.CONTENT_DISPOSITION, photo.getLabel()+photo.getFileType());
        return ResponseEntity.status(HttpStatus.OK)
                .headers(header)
                .contentType(MediaType.valueOf(photo.getFileType()))
                .body(ImageUtils.decompressImage(photo.getImage()));
    }
}
