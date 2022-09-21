package com.archive.fursuit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;

@Data
@Entity
@Table(name = "photographies")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String label;

    private String photographer;

    @JsonIgnore
    @ManyToOne
    private Event event;

    @Column(nullable = false)
    private Blob image;

    private Date date;

    public Photo(){};
}
