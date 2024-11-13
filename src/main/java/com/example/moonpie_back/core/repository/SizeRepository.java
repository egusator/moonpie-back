package com.example.moonpie_back.core.repository;

import com.example.moonpie_back.core.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    Optional<Size> findSizeByValue(String value);
}

