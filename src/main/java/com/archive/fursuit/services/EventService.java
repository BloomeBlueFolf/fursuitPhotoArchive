package com.archive.fursuit.services;

import com.archive.fursuit.Event;
import com.archive.fursuit.Photo;
import com.archive.fursuit.repositories.EventRepository;
import com.archive.fursuit.repositories.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private PhotoRepository photoRepository;


    public List<Event> showEvents() {
        return eventRepository.findAll();
    }

    public void saveEvent(Event event) {
        eventRepository.save(event);
    }

    public void deleteEvent(long id) {
        Event deletedEvent = eventRepository.findById(id);


//        for (Photo photo : deletedEvent.getPhotos()){
//            photo.assignEvent(null);
//            photoRepository.save(photo);
//        }

        eventRepository.delete(deletedEvent);
    }

    public Event getEventById(long id){
        return eventRepository.findById(id);
    }

    public void assignPhoto(Photo photo, Event event){
        event.assignPhoto(photo);
        eventRepository.save(event);
        photoRepository.save(photo);
    }

    public void renameEvent(long id, Event newEventName){
        Event renamedEvent = eventRepository.findById(id);
        renamedEvent.setLabel(newEventName.getLabel());
        eventRepository.save(renamedEvent);
    }
}
