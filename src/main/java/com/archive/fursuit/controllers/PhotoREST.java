package com.archive.fursuit.controllers;

import com.archive.fursuit.Event;
import com.archive.fursuit.Photo;
import com.archive.fursuit.services.EventService;
import com.archive.fursuit.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PhotoREST {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private EventService eventService;

    @GetMapping("photos/find")
    public List<Photo> getPhotos(){
        return photoService.findAllPhotos();
    }

    @PostMapping("photos/upload")
    public Photo uploadPhoto(@RequestBody Photo photo){
        photoService.savePhoto(photo);
        return photo;
    }

    @GetMapping("photo/delete/{id}")
    public void deletePhoto(@PathVariable ("id") long id){
        photoService.deletePhoto(id);
    }

    @GetMapping("photo/assign/{eventId}/{photoId}")
    public Event assignPhoto(@PathVariable ("eventId") long eventId, @PathVariable ("photoId") long photoId){
        Event event = eventService.getEventById(eventId);
        Photo photo = photoService.getPhotoById(photoId);
        photoService.assignEvent(photo, eventId);
        return event;
    }

}
