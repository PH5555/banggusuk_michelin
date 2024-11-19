package com.example.banggusuk_michelin.Repository;

import com.example.banggusuk_michelin.entity.RestaurantImage;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class RestaurantImageRepository {
    private final EntityManager em;

    public RestaurantImageRepository(EntityManager em) {
        this.em = em;
    }

    public RestaurantImage save(RestaurantImage restaurantImage){
        em.persist(restaurantImage);
        return restaurantImage;
    }
}
