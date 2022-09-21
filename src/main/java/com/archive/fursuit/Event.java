package com.archive.fursuit;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String label;

    @OneToMany(mappedBy = "event")
    private List<Photo> photos;

    public Event(String label) {
        this.label = label;
    }

    public Event(){};
}
