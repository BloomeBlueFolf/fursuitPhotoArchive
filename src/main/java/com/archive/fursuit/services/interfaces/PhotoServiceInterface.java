package com.archive.fursuit.services.interfaces;

import com.archive.fursuit.Photo;

import java.util.List;

public interface PhotoServiceInterface {

    public List<Photo> findAllPhotosOrderedByDate();

    public void deletePhoto(long id);

    public void savePhoto(Photo photo);

    public Photo getPhotoById(long id);

    public List<Photo> findAllPhotos();

    public void assignEvent(Photo photo, long id);

    public void updatePhoto(long id, Photo photo);

    public void movePhoto(long id, Photo photo);

    public List<Photo> findAllPhotosWithoutEvent();
}
