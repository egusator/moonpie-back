package com.example.moonpie_back.core.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@Table(name = "cart_item")
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "chest")
    private Integer chest;

    @Column(name = "waist")
    private Integer waist;

    @Column(name = "hip")
    private Integer hip;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "count", nullable = false)
    private Integer count;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private Size size;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false, referencedColumnName = "id")
    private Item item;
}
