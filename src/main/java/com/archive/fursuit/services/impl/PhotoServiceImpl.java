package com.archive.fursuit.services.impl;

import com.archive.fursuit.Event;
import com.archive.fursuit.Photo;
import com.archive.fursuit.repositories.EventRepository;
import com.archive.fursuit.repositories.PhotoRepository;
import com.archive.fursuit.services.interfaces.PhotoServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoServiceInterface {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private EventRepository eventRepository;


    public List<Photo> findAllPhotosOrderedByDate() {
        return photoRepository.findAllByOrderByDateAsc();
    }

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

    public void updatePhoto(long id, Photo photo) {
        Photo updatedPhoto = photoRepository.findById(id);
        updatedPhoto.setLabel(photo.getLabel());
        updatedPhoto.setPhotographer(photo.getPhotographer());
        updatedPhoto.setDate(photo.getDate());
        photoRepository.save(updatedPhoto);
    }

    public void movePhoto(long id, Photo photo) {
        Photo updatedPhoto = photoRepository.findById(id);
        updatedPhoto.setEvent(photo.getEvent());
        photoRepository.save(updatedPhoto);
    }

    public List<Photo> findAllPhotosWithoutEvent(){
        return photoRepository.findAll();
    }
}
