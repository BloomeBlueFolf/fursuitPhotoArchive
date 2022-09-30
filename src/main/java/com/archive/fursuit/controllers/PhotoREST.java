package com.archive.fursuit.controllers;

import com.archive.fursuit.Photo;
import com.archive.fursuit.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PhotoREST {

    @Autowired
    private PhotoService photoService;

    @GetMapping("photos/find")
    public List<Photo> getPhotos(){
        return photoService.findAllPhotos();
    }

    @PostMapping("photo/upload")
    public Photo uploadPhoto(@RequestBody Photo photo){
        photoService.savePhoto(photo);
        return photo;
    }


}
