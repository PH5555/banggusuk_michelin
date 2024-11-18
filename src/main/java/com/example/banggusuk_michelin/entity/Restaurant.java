package com.example.banggusuk_michelin.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int restaurantId;

    @Column()
    private String restaurantName;

    @Column()
    private String restaurantAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(mappedBy = "restaurant")
    private List<RestaurantImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant")
    private List<RestaurantComment> comments = new ArrayList<>();
}
