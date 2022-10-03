package com.archive.fursuit.services.impl;

import com.archive.fursuit.Event;
import com.archive.fursuit.Photo;
import com.archive.fursuit.repositories.EventRepository;
import com.archive.fursuit.repositories.PhotoRepository;
import com.archive.fursuit.services.interfaces.EventServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventServiceInterface {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Override
    public List<Event> showEvents() {
        return eventRepository.findAll();
    }

    @Override
    public void saveEvent(Event event) {
        eventRepository.save(event);
    }

    @Override
    public void deleteEvent(long id) {
        Event deletedEvent = eventRepository.findById(id);


//        for (Photo photo : deletedEvent.getPhotos()){
//            photo.assignEvent(null);
//            photoRepository.save(photo);
//        }

        eventRepository.delete(deletedEvent);
    }

    @Override
    public Event getEventById(long id){
        return eventRepository.findById(id);
    }

    @Override
    public void assignPhoto(Photo photo, Event event){
        event.assignPhoto(photo);
        eventRepository.save(event);
        photoRepository.save(photo);
    }

    @Override
    public void renameEvent(long id, Event newEventName){
        Event renamedEvent = eventRepository.findById(id);
        renamedEvent.setLabel(newEventName.getLabel());
        eventRepository.save(renamedEvent);
    }

    @Override
    public void deletePhoto(long photoId) {
        photoRepository.delete(photoRepository.findById(photoId));
    }
}
