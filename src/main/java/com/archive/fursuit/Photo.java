package com.archive.fursuit;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "photographies")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String label;

    private String photographer;

    @Column(name = "filetype")
    private String fileType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventid")
    private Event event;

    @Lob
    private byte[] image;

    private String date;

    public Photo(){};

    public Photo(String label, String photographer, String date) {
        this.label = label;
        this.photographer = photographer;
        this.date = date;
    }

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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
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
        int result = Objects.hash(id, label, photographer, event, date);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }

    public void assignEvent(Event event){
        this.event = event;
    }
}
