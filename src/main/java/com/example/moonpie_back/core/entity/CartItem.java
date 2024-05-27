package com.example.moonpie_back.core.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "chest", nullable = false)
    private Integer chest;

    @Column(name = "waist", nullable = false)
    private Integer waist;

    @Column(name = "hip", nullable = false)
    private Integer hip;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "count", nullable = false)
    private Integer count;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private Size size;

    @OneToOne
    @JoinColumn(name = "item_id", nullable = false, referencedColumnName = "id")
    private Item item;
}
