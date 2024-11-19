package com.example.banggusuk_michelin.Repository;

import com.example.banggusuk_michelin.entity.Restaurant;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class RestaurantRepository {
    private final EntityManager em;

    public RestaurantRepository(EntityManager em) {
        this.em = em;
    }

    public Restaurant save(Restaurant restaurant){
        em.persist(restaurant);
        return restaurant;
    }
}
