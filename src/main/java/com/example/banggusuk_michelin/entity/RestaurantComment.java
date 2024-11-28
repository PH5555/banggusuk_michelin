package com.example.banggusuk_michelin.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
public class RestaurantComment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int restaurantCommentId;

    @Column()
    @Getter
    private String comment;

    @Column()
    @Getter
    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    @Getter
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public RestaurantComment() {
    }

    public RestaurantComment(String comment, int rating) {
        this.comment = comment;
        this.rating = rating;
    }
}
