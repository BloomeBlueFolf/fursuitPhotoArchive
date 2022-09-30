package com.archive.fursuit;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "photographies")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String label;

    private String photographer;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "eventid")
    @JsonIgnore
    private Event event;

    //@Column(nullable = false)
    private Blob image;

    private Date date;

    public Photo(){};

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

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return id == photo.id && Objects.equals(label, photo.label) && Objects.equals(photographer, photo.photographer) && Objects.equals(event, photo.event) && Objects.equals(image, photo.image) && Objects.equals(date, photo.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, photographer, event, image, date);
    }

    public void assignEvent(Event event){
        this.event = event;
        event.getPhotos().add(this);
    }
}
