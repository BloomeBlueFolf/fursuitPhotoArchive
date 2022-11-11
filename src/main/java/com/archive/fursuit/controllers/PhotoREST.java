package com.archive.fursuit.controllers;

import com.archive.fursuit.ImageUtils;
import com.archive.fursuit.Photo;
import com.archive.fursuit.User;
import com.archive.fursuit.services.impl.EventServiceImpl;
import com.archive.fursuit.services.impl.PhotoServiceImpl;
import com.archive.fursuit.services.impl.UserServiceImpl;
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

        List<Photo> allPhotos = photoServiceImpl.findAllPhotos();
        if (allPhotos.isEmpty()) {
            return new ResponseEntity<>("Currently there exist no photographies.", HttpStatus.NOT_FOUND);
        } else {
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

        User authUser = userService.findUser(header.get("username"));
        if(authUser != null && passwordEncoder.matches(header.get("password"), authUser.getPassword())) {
            photoServiceImpl.createNewPhoto(new Photo(label, photographer, date), eventId, file);
            return new ResponseEntity<>("Photography succesfully uploaded.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Wrong credentials. Try again.", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("private/photo/delete/{id}")
    public ResponseEntity<?> deletePhoto(@PathVariable ("id") long id,
                                         @RequestHeader Map<String, String> header){

        User authUser = userService.findUser(header.get("username"));
        if(authUser != null && passwordEncoder.matches(header.get("password"), authUser.getPassword())) {
            Photo deletedPhoto = photoServiceImpl.getPhotoById(id);
            if (deletedPhoto == null) {
                return new ResponseEntity<>(String.format("A photo with ID %s doesn't exist.", id), HttpStatus.NOT_FOUND);
            } else {
                photoServiceImpl.deletePhoto(id);
                return new ResponseEntity<>(String.format("Photo with ID %s successfully deleted.", id), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("Wrong credentials. Try again.", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("private/photo/move/{photoId}/{eventId}")
    public ResponseEntity<?> movePhoto(@PathVariable ("photoId") long photoId,
                                       @PathVariable ("eventId") long eventId,
                                       @RequestHeader Map<String, String> header){

        User authUser = userService.findUser(header.get("username"));
        if(authUser != null && passwordEncoder.matches(header.get("password"), authUser.getPassword())) {
            Photo movedPhoto = photoServiceImpl.getPhotoById(photoId);
            if (movedPhoto == null) {
                return new ResponseEntity<>(String.format("A photo with ID %s doesn't exist.", photoId), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(photoServiceImpl.assignEvent(movedPhoto, eventId), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("Wrong credentials. Try again.", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("private/photo/edit/{id}")
    public ResponseEntity<?> editPhoto(@PathVariable ("id") long id,
                                       @RequestBody Photo photo,
                                       @RequestHeader Map<String, String> header){

        User authUser = userService.findUser(header.get("username"));
        if(authUser != null && passwordEncoder.matches(header.get("password"), authUser.getPassword())) {
            Photo editedPhoto = photoServiceImpl.getPhotoById(id);
            if (editedPhoto == null) {
                return new ResponseEntity<>("There exists no photography with this ID.", HttpStatus.NOT_FOUND);
            } else {
                editedPhoto.setLabel(photo.getLabel());
                editedPhoto.setPhotographer(photo.getPhotographer());
                editedPhoto.setDate(photo.getDate());
                photoServiceImpl.savePhoto(editedPhoto);
                return new ResponseEntity<>(editedPhoto, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("Wrong credentials. Try again.", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("public/photo/download/{id}")
    public ResponseEntity<?> getImage(@PathVariable("id") long id,
                                      @RequestHeader Map<String, String> header){

        Photo photo = photoServiceImpl.getPhotoById(id);
        if(photo != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf(photo.getFileType()))
                    .body(ImageUtils.decompressImage(photo.getImage()));
        } else {
            return  ResponseEntity.status(HttpStatus.OK).body(String.format("Image with ID %s not available.", id));
        }
    }
}
