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
    private EventRepository eventrepository;

    @Autowired
    private PhotoRepository photoRepository;


    public List<Event> showEvents() {
        return eventrepository.findAll();
    }

    public void saveEvent(Event event) {
        eventrepository.save(event);
    }

    public void deleteEvent(long id) {
        Event deletedEvent = eventrepository.findById(id).get();

        for (Photo photo : deletedEvent.getPhotos()){
            photo.assignEvent(null);
            photoRepository.save(photo);
        }

        eventrepository.delete(deletedEvent);
    }

    public Event getEventById(long id){
        return eventrepository.findById(id).get();
    }

    public void addPhoto(Photo photo, Event event){
        event.addPhoto(photo);
        //eventrepository.save(event);
    }
}
