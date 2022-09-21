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

    public Boolean createEvent(String label) {
        Event event = new Event(label);
        eventrepository.save(event);
        return true;
    }

    public Boolean deleteEvent(int id, Event event) {
        Event deletedEvent = eventrepository.findById(id).get();

        for (Photo photo : event.getPhotos()){
            photo.setEvent(null);
            photoRepository.save(photo);
        }
        return true;
    }

    public List<Event> findAllEvents(){
        return eventrepository.findAll();
    }
}
