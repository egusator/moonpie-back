package com.example.moonpie_back.core.entity;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "photos")
public class Photo {
    @Getter
    @Id
    private String id;

    @Setter
    private Binary image;

}
