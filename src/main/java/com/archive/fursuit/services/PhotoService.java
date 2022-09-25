package com.archive.fursuit.services;

import com.archive.fursuit.Photo;
import com.archive.fursuit.repositories.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;


    public List<Photo> findAllPhotosOrderedByDate() {
        return photoRepository.findAllByOrderByDateAsc();
    }

    public void deletePhoto(int id) {
        Photo photo = photoRepository.findById(id).get();
        photoRepository.delete(photo);
    }

    public void addNewPhoto(Photo photo){
        photoRepository.save(photo);
    }

    public void updatePhoto(int id, Photo photo) {
        Photo updatedPhoto = photoRepository.findById(id).get();
        updatedPhoto.setLabel(photo.getLabel());
        updatedPhoto.setPhotographer(photo.getPhotographer());
        updatedPhoto.setDate(photo.getDate());
        photoRepository.save(updatedPhoto);
    }

    public void movePhoto(int id, Photo photo) {
        Photo updatedPhoto = photoRepository.findById(id).get();
        updatedPhoto.setEvent(photo.getEvent());
        photoRepository.save(updatedPhoto);
    }
}
