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

    @OneToMany(mappedBy = "restaurant")
    private List<RestaurantGroup> restaurantGroups = new ArrayList<>();

    public Restaurant() {
    }

    public Restaurant(String restaurantName, String restaurantAddress, String latitude, String longitude) {
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
