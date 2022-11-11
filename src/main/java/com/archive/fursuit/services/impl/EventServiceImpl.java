package com.archive.fursuit.services.impl;

import com.archive.fursuit.Event;
import com.archive.fursuit.Photo;
import com.archive.fursuit.repositories.EventRepository;
import com.archive.fursuit.repositories.PhotoRepository;
import com.archive.fursuit.services.interfaces.EventServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventServiceInterface {

    Logger logger = LoggerFactory.getLogger("EventServiceImpl");

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Override
    public List<Event> showEventsOrdered() {
        logger.info("Get all events ordered by ID from database.");
        return eventRepository.findAllByOrderByIdDesc();
    }

    @Override
    public void saveEvent(Event event) {
        logger.info("Save event in database.");
        eventRepository.save(event);
    }

    public List<Event> getAllEvents(){
        logger.info("Get all events from database.");
        return eventRepository.findAll();
    }

    @Override
    public void deleteEvent(long id) {
        Event deletedEvent = eventRepository.findById(id);
        logger.info("Delete event from database.");
        eventRepository.delete(deletedEvent);
    }

    @Override
    public Event getEventById(long id){
        logger.info("Get event by ID from database.");
        return eventRepository.findById(id);
    }

    @Override
    public void renameEvent(long id, Event newEventName){
        logger.info("Get event by ID from database.");
        Event renamedEvent = eventRepository.findById(id);
        renamedEvent.setLabel(newEventName.getLabel());
        logger.info("Save event in database.");
        eventRepository.save(renamedEvent);
    }

    @Override
    public void deletePhoto(long photoId) {
        logger.info("Find photography by ID from database and delete it.");
        photoRepository.delete(photoRepository.findById(photoId));
    }

    @Override
    public List<Photo> sortPhotosByIdDesc(Event event){
        List<Photo> photoList = event.getPhotos();
        logger.info("List of all photographies created.");
            List <Photo> invertedList = new ArrayList<>();
            for (int i = photoList.size() - 1; i >= 0; i--) {
                invertedList.add(photoList.get(i));
        }
        return invertedList;
    }
}
