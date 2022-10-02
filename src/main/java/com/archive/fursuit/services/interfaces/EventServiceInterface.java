package com.archive.fursuit.services.interfaces;

import com.archive.fursuit.Event;

import java.util.List;

public interface EventServiceInterface {

    public List<Event> showEvents();

    public void saveEvent(Event event);

    public void deleteEvent(long id);

    public Event getEventById(long id);

    public void renameEvent(long id, Event newEventName);
}
