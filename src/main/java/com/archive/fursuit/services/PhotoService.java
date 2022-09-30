package com.archive.fursuit.services;

import com.archive.fursuit.Event;
import com.archive.fursuit.Photo;
import com.archive.fursuit.repositories.EventRepository;
import com.archive.fursuit.repositories.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private EventRepository eventRepository;


    public List<Photo> findAllPhotosOrderedByDate() {
        return photoRepository.findAllByOrderByDateAsc();
    }

    public void deletePhoto(long id) {
        Photo photo = photoRepository.findById(id).get();
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
        photo.assignEvent(eventRepository.findById(id).get());
        photoRepository.save(photo);
        //eventRepository.save(event);
    }

    public void updatePhoto(long id, Photo photo) {
        Photo updatedPhoto = photoRepository.findById(id).get();
        updatedPhoto.setLabel(photo.getLabel());
        updatedPhoto.setPhotographer(photo.getPhotographer());
        updatedPhoto.setDate(photo.getDate());
        photoRepository.save(updatedPhoto);
    }

    public void movePhoto(long id, Photo photo) {
        Photo updatedPhoto = photoRepository.findById(id).get();
        updatedPhoto.setEvent(photo.getEvent());
        photoRepository.save(updatedPhoto);
    }

    public List<Photo> findAllPhotosWithoutEvent(){
        return photoRepository.findAll();
    }
}
