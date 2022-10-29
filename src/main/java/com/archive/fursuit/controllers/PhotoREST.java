package com.archive.fursuit.controllers;

import com.archive.fursuit.Event;
import com.archive.fursuit.Photo;
import com.archive.fursuit.services.impl.EventServiceImpl;
import com.archive.fursuit.services.impl.PhotoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class PhotoREST {

    @Autowired
    private PhotoServiceImpl photoServiceImpl;

    @Autowired
    private EventServiceImpl eventServiceImpl;

    @GetMapping("public/photos/findAll")
    public List<Photo> getPhotos() {
        List<Photo> allPhotos = photoServiceImpl.findAllPhotos();
        if (allPhotos.isEmpty()) {
            return null;
        } else {
            return allPhotos;
        }
    }

    @PostMapping("upload")
    public Photo uploadPhoto(@RequestBody Photo photo){
        photoServiceImpl.savePhoto(photo);
        return photo;
    }

    @DeleteMapping("private/photo/delete/{id}")
    public String deletePhoto(@PathVariable ("id") long id){
        Photo deletedPhoto = photoServiceImpl.getPhotoById(id);
        if(deletedPhoto == null) {
            return String.format("A photo with ID %s doesn't exist.", id);
        }
        else {
            photoServiceImpl.deletePhoto(id);
            return String.format("Photo with ID %s successfully deleted.", id);
        }
    }

    @PostMapping("private/photo/move/{photoId}/{eventId}")
    public String movePhoto(@PathVariable ("photoId") long photoId, @PathVariable ("eventId") long eventId){
        Photo movedPhoto = photoServiceImpl.getPhotoById(photoId);
        if(movedPhoto == null) {
            return String.format("A photo with ID %s doesn't exist.", photoId);
        }
        else {
            return photoServiceImpl.assignEvent(movedPhoto, eventId);
        }
    }

    @PutMapping("private/photo/edit/{id}")
    public Photo editPhoto(@PathVariable ("id") long id, @RequestBody Photo photo){
        Photo editedPhoto = photoServiceImpl.getPhotoById(id);
        if(editedPhoto == null){
            return null;
        }
        else {
            editedPhoto.setLabel(photo.getLabel());
            editedPhoto.setPhotographer(photo.getPhotographer());
            editedPhoto.setDate(photo.getDate());
            photoServiceImpl.savePhoto(editedPhoto);
            return editedPhoto;
        }
    }


    //downloadPhoto

    //uploadPhoto with image
}
