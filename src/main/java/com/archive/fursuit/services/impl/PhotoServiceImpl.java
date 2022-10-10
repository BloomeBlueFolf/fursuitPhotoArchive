package com.archive.fursuit.services.impl;

import com.archive.fursuit.Event;
import com.archive.fursuit.Photo;
import com.archive.fursuit.repositories.EventRepository;
import com.archive.fursuit.repositories.PhotoRepository;
import com.archive.fursuit.services.interfaces.PhotoServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoServiceInterface {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private EventRepository eventRepository;

    public void deletePhoto(long id) {
        Photo photo = photoRepository.findById(id);
        photoRepository.delete(photo);
    }

    public void savePhoto(Photo photo){
        photoRepository.save(photo);
    }

    public Photo getPhotoById(long id){
        return photoRepository.findPhotoById(id);
    }

    public List<Photo> findAllPhotos(){
        return photoRepository.findAll();
    }

    public void assignEvent(Photo photo, long id){
        Event event = eventRepository.findById(id);
        photo.assignEvent(event);
        photoRepository.save(photo);
    }

    public List<Photo> findAllPhotosWithoutEvent(){
        return photoRepository.findAll();
    }

    public byte[] uploadImageData(Photo photo, MultipartFile file) throws IOException {
        return file.getBytes();
    }
}
