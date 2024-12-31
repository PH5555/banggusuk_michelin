package com.example.banggusuk_michelin.entity;

import jakarta.persistence.*;

@Entity
public class RestaurantGroup {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "uid")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}
