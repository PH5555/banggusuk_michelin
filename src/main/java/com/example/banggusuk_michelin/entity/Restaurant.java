package com.example.banggusuk_michelin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int restaurantId;

    @Column()
    private String restaurantName;

    @Column()
    private String restaurantAddress;

    @Column
    private String image;

    @Column
    private String latitude;

    @Column
    private String longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<RestaurantComment> comments = new ArrayList<>();

    public Restaurant() {
    }

    public Restaurant(String restaurantName, String restaurantAddress) {
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
    }
}
