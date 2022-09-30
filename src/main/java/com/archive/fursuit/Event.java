package com.archive.fursuit;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String label;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private List<Photo> photos = new LinkedList<>();

    public Event(String label) {
        this.label = label;
    }

    public Event(){};

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id && Objects.equals(label, event.label) && Objects.equals(photos, event.photos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, photos);
    }

    public void addPhoto(Photo photo){
        photos.add(photo);
    }
}
