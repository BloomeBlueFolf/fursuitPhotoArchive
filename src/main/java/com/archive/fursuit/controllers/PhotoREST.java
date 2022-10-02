package com.archive.fursuit.controllers;

import com.archive.fursuit.Event;
import com.archive.fursuit.Photo;
import com.archive.fursuit.services.impl.EventServiceImpl;
import com.archive.fursuit.services.impl.PhotoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PhotoREST {

    @Autowired
    private PhotoServiceImpl photoServiceImpl;

    @Autowired
    private EventServiceImpl eventServiceImpl;

    @GetMapping("photos/find")
    public List<Photo> getPhotos(){
        return photoServiceImpl.findAllPhotos();
    }

    @PostMapping("photos/upload")
    public Photo uploadPhoto(@RequestBody Photo photo){
        photoServiceImpl.savePhoto(photo);
        return photo;
    }

    @GetMapping("photo/delete/{id}")
    public void deletePhoto(@PathVariable ("id") long id){
        photoServiceImpl.deletePhoto(id);
    }

    @GetMapping("photo/assign/{eventId}/{photoId}")
    public Event assignPhoto(@PathVariable ("eventId") long eventId, @PathVariable ("photoId") long photoId){
        Event event = eventServiceImpl.getEventById(eventId);
        Photo photo = photoServiceImpl.getPhotoById(photoId);
        photoServiceImpl.assignEvent(photo, eventId);
        return event;
    }

}
