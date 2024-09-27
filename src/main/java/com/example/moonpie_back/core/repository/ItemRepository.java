package com.example.moonpie_back.core.repository;

import com.example.moonpie_back.api.dto.ItemCategory;
import com.example.moonpie_back.core.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByCategory(ItemCategory category);

    Item findByName(String name);
}
