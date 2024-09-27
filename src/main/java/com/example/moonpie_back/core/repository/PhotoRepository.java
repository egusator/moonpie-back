package com.example.moonpie_back.core.repository;

import com.example.moonpie_back.core.entity.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhotoRepository extends MongoRepository<Photo, String> { }
