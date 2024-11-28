package com.example.banggusuk_michelin.entity;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Setter
public class RestaurantImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column
    private String image;

    public RestaurantImage(Restaurant restaurant, String image) {
        this.restaurant = restaurant;
        this.image = image;
    }

    public RestaurantImage() {
    }
}
