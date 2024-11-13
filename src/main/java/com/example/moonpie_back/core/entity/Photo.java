package com.example.moonpie_back.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "photos")
@NoArgsConstructor
@AllArgsConstructor
public class Photo {


    @Id
    private Long id;

    @Column(name="url")
    private String url;

}
