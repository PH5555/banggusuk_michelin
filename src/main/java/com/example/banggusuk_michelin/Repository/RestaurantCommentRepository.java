package com.example.banggusuk_michelin.Repository;

import com.example.banggusuk_michelin.entity.RestaurantComment;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class RestaurantCommentRepository {
    private final EntityManager em;

    public RestaurantCommentRepository(EntityManager em) {
        this.em = em;
    }

    public RestaurantComment save(RestaurantComment restaurantComment){
        em.persist(restaurantComment);
        return restaurantComment;
    }
}
