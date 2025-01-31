package com.gyma.gyma.repository;

import com.gyma.gyma.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    Optional<Image> findByIdObject(String idObject);
}
