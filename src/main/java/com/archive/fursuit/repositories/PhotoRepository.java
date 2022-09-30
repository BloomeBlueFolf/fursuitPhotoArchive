package com.archive.fursuit.repositories;

import com.archive.fursuit.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    public List<Photo> findAllByOrderByDateAsc();

    public Photo findPhotoById(long id);
}
