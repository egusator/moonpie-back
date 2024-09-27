package com.example.moonpie_back.core.entity;

import com.example.moonpie_back.api.dto.ItemCategory;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@Table(name = "item")
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "quantity_in_stock", nullable = false)
    private Integer quantityInStock;

    @Version
    private Long version;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "category", nullable = false)
    private String category;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(name = "item_size",
            joinColumns = {@JoinColumn(name = "item_id")},
            inverseJoinColumns = {@JoinColumn(name = "size_id")}
    )
    private Set<Size> sizes;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(name = "item_color",
            joinColumns = {@JoinColumn(name = "item_id")},
            inverseJoinColumns = {@JoinColumn(name = "color_id")}
    )
    private Set<Color> colors;
}
