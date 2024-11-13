package com.example.moonpie_back.core.repository;

import com.example.moonpie_back.core.enums.ItemCategory;
import com.example.moonpie_back.core.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = """
                WITH RECURSIVE CategoryTree AS (
                SELECT id FROM category WHERE name = :categoryName
                UNION ALL
                SELECT c.id FROM category c
                INNER JOIN CategoryTree ct ON ct.id = c.parent_id
            )
            SELECT * FROM Item i WHERE (i.category_id IN (SELECT id FROM CategoryTree) )
            AND i.to_display = TRUE
            LIMIT :limit OFFSET :offset
            """,
            nativeQuery = true)
    List<Item> findItemsByCategoryName(
            @Param("categoryName") String categoryName,
            @Param("limit") int limit,
            @Param("offset") int offset);

    List<Item> findAllByCategory(ItemCategory category);

    @Query(value = """
            SELECT * FROM Item i WHERE i.to_display = TRUE
            LIMIT :limit OFFSET :offset
            """, nativeQuery = true)
    List<Item> findItemsLimitOffset(@Param("limit") int limit, @Param("offset") int offset);

    Item findByName(String name);

    Optional<Item> findById(Long id);
}
