package com.archive.fursuit.services.interfaces;

import com.archive.fursuit.Event;
import com.archive.fursuit.Photo;

import java.util.List;

public interface EventServiceInterface {

    public List<Event> showEventsOrdered();

    public void saveEvent(Event event);

    public void deleteEvent(long id);

    public Event getEventById(long id);

    public void renameEvent(long id, Event newEventName);

    public void deletePhoto(long photo_id);

    public List<Photo> sortPhotosByIdDesc(Event event);
}
