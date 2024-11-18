package com.example.banggusuk_michelin.entity;

import jakarta.persistence.*;

@Entity
public class RestaurantComment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int restaurantCommentId;

    @Column()
    private String comment;

    @Column()
    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
