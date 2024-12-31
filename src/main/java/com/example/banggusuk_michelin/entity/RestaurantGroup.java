package com.example.banggusuk_michelin.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class RestaurantGroup {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int restaurantGroupId;

    @ManyToOne
    @JoinColumn(name = "uid")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(mappedBy = "restaurantGroup")
    private List<RestaurantComment> restaurantComments = new ArrayList<>();

    public void addComment(RestaurantComment comment) {
        restaurantComments.add(comment);
        comment.setRestaurantGroup(this);
    }
}
