package com.archive.fursuit.controllers;

import com.archive.fursuit.ImageUtils;
import com.archive.fursuit.Photo;
import com.archive.fursuit.User;
import com.archive.fursuit.services.impl.EventServiceImpl;
import com.archive.fursuit.services.impl.PhotoServiceImpl;
import com.archive.fursuit.services.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class PhotoREST {

    Logger logger = LoggerFactory.getLogger("PhotoREST");

    @Autowired
    private PhotoServiceImpl photoServiceImpl;

    @Autowired
    private EventServiceImpl eventServiceImpl;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("public/photos/findAll")
    public ResponseEntity<?> getPhotos() {
        logger.info("API show all photographies called.");

        List<Photo> allPhotos = photoServiceImpl.findAllPhotos();
        if (allPhotos.isEmpty()) {
            logger.error("No photgraphies found.");
            return new ResponseEntity<>("Currently there exist no photographies.", HttpStatus.NOT_FOUND);
        } else {
            logger.info("Return list of all photographies.");
            return new ResponseEntity<>(allPhotos, HttpStatus.OK);
        }
    }

    @PostMapping("private/photo/upload/{eventId}")
    public ResponseEntity<?> uploadPhoto(@RequestParam ("file") MultipartFile file,
                                         @RequestHeader Map<String, String> header,
                                         @RequestParam ("label") String label,
                                         @RequestParam ("photographer") String photographer,
                                         @RequestParam ("date") String date,
                                         @PathVariable ("eventId") long eventId) {
        logger.info("API photography upload called.");

        User authUser = userService.findUser(header.get("username"));
        logger.info("Check credentials.");
        if(authUser != null && passwordEncoder.matches(header.get("password"), authUser.getPassword())) {
            logger.info("Credentials ok.");
            photoServiceImpl.createNewPhoto(new Photo(label, photographer, date), eventId, file);
            return new ResponseEntity<>("Photography succesfully uploaded.", HttpStatus.OK);
        } else {
            logger.info("Credentials not ok.");
            return new ResponseEntity<>("Wrong credentials. Try again.", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("private/photo/delete/{id}")
    public ResponseEntity<?> deletePhoto(@PathVariable ("id") long id,
                                         @RequestHeader Map<String, String> header){
        logger.info("API delete photography called.");

        User authUser = userService.findUser(header.get("username"));
        logger.info("Check credentials.");
        if(authUser != null && passwordEncoder.matches(header.get("password"), authUser.getPassword())) {
            logger.info("Credentials ok.");
            Photo deletedPhoto = photoServiceImpl.getPhotoById(id);
            if (deletedPhoto == null) {
                logger.error("Photography not found.");
                return new ResponseEntity<>(String.format("A photo with ID %s doesn't exist.", id), HttpStatus.NOT_FOUND);
            } else {
                photoServiceImpl.deletePhoto(id);
                return new ResponseEntity<>(String.format("Photo with ID %s successfully deleted.", id), HttpStatus.OK);
            }
        } else {
            logger.info("Credentials not ok.");
            return new ResponseEntity<>("Wrong credentials. Try again.", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("private/photo/move/{photoId}/{eventId}")
    public ResponseEntity<?> movePhoto(@PathVariable ("photoId") long photoId,
                                       @PathVariable ("eventId") long eventId,
                                       @RequestHeader Map<String, String> header){
        logger.info("API move photography called.");

        User authUser = userService.findUser(header.get("username"));
        logger.info("Check credentials.");
        if(authUser != null && passwordEncoder.matches(header.get("password"), authUser.getPassword())) {
            logger.info("Credentials ok.");
            Photo movedPhoto = photoServiceImpl.getPhotoById(photoId);
            if (movedPhoto == null) {
                logger.error("Photography not found.");
                return new ResponseEntity<>(String.format("A photo with ID %s doesn't exist.", photoId), HttpStatus.NOT_FOUND);
            } else {
                logger.info("Return event with assigned photography.");
                return new ResponseEntity<>(photoServiceImpl.assignEvent(movedPhoto, eventId), HttpStatus.OK);
            }
        } else {
            logger.info("Credentials not ok.");
            return new ResponseEntity<>("Wrong credentials. Try again.", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("private/photo/edit/{id}")
    public ResponseEntity<?> editPhoto(@PathVariable ("id") long id,
                                       @RequestBody Photo photo,
                                       @RequestHeader Map<String, String> header){
        logger.info("API edit photography called.");

        User authUser = userService.findUser(header.get("username"));
        logger.info("Check credentials.");
        if(authUser != null && passwordEncoder.matches(header.get("password"), authUser.getPassword())) {
            logger.info("Credentials ok.");
            Photo editedPhoto = photoServiceImpl.getPhotoById(id);
            if (editedPhoto == null) {
                logger.error("Photography not found.");
                return new ResponseEntity<>("There exists no photography with this ID.", HttpStatus.NOT_FOUND);
            } else {
                editedPhoto.setLabel(photo.getLabel());
                editedPhoto.setPhotographer(photo.getPhotographer());
                editedPhoto.setDate(photo.getDate());
                photoServiceImpl.savePhoto(editedPhoto);
                logger.info("Return edited photography.");
                return new ResponseEntity<>(editedPhoto, HttpStatus.OK);
            }
        } else {
            logger.info("Credentials not ok.");
            return new ResponseEntity<>("Wrong credentials. Try again.", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("public/photo/download/{id}")
    public ResponseEntity<?> getImage(@PathVariable("id") long id,
                                      @RequestHeader Map<String, String> header){
        logger.info("API download photography called.");

        Photo photo = photoServiceImpl.getPhotoById(id);
        if(photo != null) {
            logger.info("Return photography for download.");
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf(photo.getFileType()))
                    .body(ImageUtils.decompressImage(photo.getImage()));
        } else {
            logger.error("Photography not found.");
            return  ResponseEntity.status(HttpStatus.OK).body(String.format("Image with ID %s not available.", id));
        }
    }
}
