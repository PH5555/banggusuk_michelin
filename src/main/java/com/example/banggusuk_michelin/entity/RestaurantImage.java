package com.example.banggusuk_michelin.entity;

import jakarta.persistence.*;

@Entity
public class RestaurantImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column
    private String image;
}
