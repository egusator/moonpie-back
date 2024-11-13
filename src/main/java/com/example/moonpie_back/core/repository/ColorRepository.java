package com.example.moonpie_back.core.repository;

import com.example.moonpie_back.core.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
    Optional<Color> findColorByValue(String value);

}
