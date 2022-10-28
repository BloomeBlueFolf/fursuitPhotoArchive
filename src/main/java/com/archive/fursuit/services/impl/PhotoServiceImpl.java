package com.archive.fursuit.services.impl;

import com.archive.fursuit.Event;
import com.archive.fursuit.ImageUtils;
import com.archive.fursuit.Photo;
import com.archive.fursuit.repositories.EventRepository;
import com.archive.fursuit.repositories.PhotoRepository;
import com.archive.fursuit.services.interfaces.PhotoServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoServiceInterface {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private EventRepository eventRepository;

    @Override
    public void deletePhoto(long id) {
        Photo photo = photoRepository.findById(id);
        photoRepository.delete(photo);
    }

    @Override
    public void savePhoto(Photo photo){
        photoRepository.save(photo);
    }

    @Override
    public Photo getPhotoById(long id){
        return photoRepository.findPhotoById(id);
    }

    @Override
    public List<Photo> findAllPhotos(){
        return photoRepository.findAll();
    }

    @Override
    public void assignEvent(Photo photo, long id){
        Event event = eventRepository.findById(id);
        photo.assignEvent(event);
        photoRepository.save(photo);
    }

    @Override
    public List<Photo> findAllPhotosWithoutEvent(){
        return photoRepository.findAll();
    }

    @Override
    public String createNewPhoto(Photo formPhoto, long id, MultipartFile file) {
        Photo newPhoto = new Photo();
        newPhoto.setLabel(formPhoto.getLabel());
        newPhoto.setPhotographer(formPhoto.getPhotographer());
        newPhoto.setDate(formPhoto.getDate());
        if (file.getContentType().contains("image")){
            try {
                newPhoto.setFileType(file.getContentType());
                newPhoto.setImage(ImageUtils.compressImage(file.getBytes()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.assignEvent(newPhoto, id);
            return "success";
        }
        else {
            return "failed";
        }
    }
}
