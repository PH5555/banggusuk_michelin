package com.example.banggusuk_michelin.Repository;

import com.example.banggusuk_michelin.entity.Group;
import com.example.banggusuk_michelin.entity.Restaurant;
import com.example.banggusuk_michelin.entity.RestaurantGroup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RestaurantGroupRepository {
    private EntityManager em;

    public RestaurantGroupRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Restaurant restaurant, Group group) {
        RestaurantGroup restaurantGroup = new RestaurantGroup();
        restaurantGroup.setRestaurant(restaurant);
        restaurantGroup.setGroup(group);
        em.persist(restaurantGroup);
    }

    public Optional<RestaurantGroup> searchRestaurantByGroupId(String groupId) {
        TypedQuery<RestaurantGroup> query = em.createQuery("select r from RestaurantGroup r where r.group.groupId = :groupId", RestaurantGroup.class);
        query.setParameter("groupId", groupId);
        return query.getResultStream().findAny();
    }
}
