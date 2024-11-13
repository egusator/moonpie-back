package com.example.moonpie_back.core.repository;

import com.example.moonpie_back.core.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> { }
