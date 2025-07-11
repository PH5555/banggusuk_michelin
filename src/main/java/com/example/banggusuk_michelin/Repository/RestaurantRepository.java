package com.example.banggusuk_michelin.Repository;

import com.example.banggusuk_michelin.entity.Group;
import com.example.banggusuk_michelin.entity.Restaurant;
import com.example.banggusuk_michelin.entity.RestaurantGroup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

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

    public Restaurant findById(int id){
        return em.find(Restaurant.class, id);
    }

    public Optional<Restaurant> findByAddress(String address){
        TypedQuery<Restaurant> query = em.createQuery("select r from Restaurant r where r.restaurantAddress = :address", Restaurant.class);
        query.setParameter("address", address);
        return query.getResultStream().findFirst();
    }
}
