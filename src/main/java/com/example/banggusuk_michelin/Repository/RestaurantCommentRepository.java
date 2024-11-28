package com.example.banggusuk_michelin.Repository;

import com.example.banggusuk_michelin.entity.RestaurantComment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<RestaurantComment> findAll(){
        TypedQuery<RestaurantComment> result = em.createQuery("select r from RestaurantComment r", RestaurantComment.class);
        return result.getResultList();
    }
}
