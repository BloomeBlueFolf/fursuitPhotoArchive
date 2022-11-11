package com.archive.fursuit.services.impl;

import com.archive.fursuit.Event;
import com.archive.fursuit.ImageUtils;
import com.archive.fursuit.Photo;
import com.archive.fursuit.repositories.EventRepository;
import com.archive.fursuit.repositories.PhotoRepository;
import com.archive.fursuit.services.interfaces.PhotoServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoServiceInterface {

    Logger logger = LoggerFactory.getLogger("PhotoServiceImpl");

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private EventRepository eventRepository;

    @Override
    public void deletePhoto(long id) {
        logger.info("Get photography by ID from database.");
        Photo photo = photoRepository.findById(id);
        logger.info("Delete photography from database.");
        photoRepository.delete(photo);
    }

    @Override
    public void savePhoto(Photo photo){
        logger.info("Save photography in database.");
        photoRepository.save(photo);
    }

    @Override
    public Photo getPhotoById(long id){
        logger.info("Get photography by ID from database.");
        return photoRepository.findById(id);
    }

    @Override
    public List<Photo> findAllPhotos(){
        logger.info("Get all photographies from database.");
        return photoRepository.findAll();
    }

    @Override
    public String assignEvent(Photo photo, long id){
        logger.info("Assign photography to event.");
        logger.info("Get event by ID from database.");
        Event event = eventRepository.findById(id);
        if (event == null){
            logger.error("There is no event with this ID.");
            return String.format("A event with ID %s doesn't exist.", id);
        }
        else {
            logger.info(String.format("Assign photography to event %s.", event));
            photo.assignEvent(event);
            logger.info("Save photography to database.");
            photoRepository.save(photo);
            return String.format("Photo %s successfully moved to %s.", photo.getLabel(), event.getLabel());
        }
    }

    @Override
    public String createNewPhoto(Photo formPhoto, long id, MultipartFile file) {
        logger.info("Create new photography.");
        Photo newPhoto = new Photo();
        newPhoto.setLabel(formPhoto.getLabel());
        newPhoto.setPhotographer(formPhoto.getPhotographer());
        newPhoto.setDate(formPhoto.getDate());
        logger.info("Check if uploaded file is image type.");
        if (file.getContentType().contains("image")){
            try {
                logger.info("Try to get file information.");
                logger.info("Set content type.");
                newPhoto.setFileType(file.getContentType());
                logger.info("Set compressed image bytes.");
                newPhoto.setImage(ImageUtils.compressImage(file.getBytes()));
            } catch (Exception e) {
                logger.error("File information could not be retrieved.");
                e.printStackTrace();
            }
            this.assignEvent(newPhoto, id);
            logger.info("Return success message.");
            return "success";
        }
        else {
            logger.info("Return failure message.");
            return "failed";
        }
    }
}
