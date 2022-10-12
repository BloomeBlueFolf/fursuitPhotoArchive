package com.archive.fursuit.services.interfaces;

import com.archive.fursuit.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhotoServiceInterface {

    public void deletePhoto(long photoId);

    public void savePhoto(Photo photo);

    public Photo getPhotoById(long id);

    public List<Photo> findAllPhotos();

    public void assignEvent(Photo photo, long id);

    public List<Photo> findAllPhotosWithoutEvent();

    public void createNewPhoto(Photo photo, long id, MultipartFile file);
}
