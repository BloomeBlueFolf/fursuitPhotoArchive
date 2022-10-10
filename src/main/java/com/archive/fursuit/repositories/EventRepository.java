package com.archive.fursuit.repositories;

import com.archive.fursuit.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    public Event findById(long id);

    public List<Event> findAllByOrderByIdDesc();
}
