package com.example.banggusuk_michelin.Repository;

import com.example.banggusuk_michelin.entity.Group;
import com.example.banggusuk_michelin.entity.Restaurant;
import com.example.banggusuk_michelin.entity.RestaurantComment;
import com.example.banggusuk_michelin.entity.RestaurantGroup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RestaurantGroupRepository {
    private EntityManager em;

    public RestaurantGroupRepository(EntityManager em) {
        this.em = em;
    }

    public RestaurantGroup save(Restaurant restaurant, Group group, RestaurantComment comment) {
        RestaurantGroup restaurantGroup = new RestaurantGroup();
        restaurantGroup.setRestaurant(restaurant);
        restaurantGroup.setGroup(group);
        restaurantGroup.addComment(comment);
        em.persist(restaurantGroup);
        return restaurantGroup;
    }

    public RestaurantGroup addComment(RestaurantGroup restaurantGroup, RestaurantComment comment) {
        restaurantGroup.addComment(comment);
        em.flush();
        return restaurantGroup;
    }

    public Optional<RestaurantGroup> searchRestaurantByGroupId(Restaurant restaurant, String groupId) {
        TypedQuery<RestaurantGroup> query = em.createQuery("select r from RestaurantGroup r where r.group.groupId = :groupId and r.restaurant = :restaurant", RestaurantGroup.class);
        query.setParameter("groupId", groupId);
        query.setParameter("restaurant", restaurant);
        return query.getResultStream().findAny();
    }

    public List<RestaurantGroup> findInCurrentGroup(Group group, int rating){
        TypedQuery<RestaurantGroup> query = em.createQuery("select rg from RestaurantGroup rg join rg.restaurantComments as c " +
                "where rg.restaurantGroupId = c.restaurantGroup.restaurantGroupId and rg.group = :group group by rg.restaurantGroupId having avg(c.rating) >= :rating", RestaurantGroup.class);
        query.setParameter("group", group);
        query.setParameter("rating", rating);
        return query.getResultList();
    }
}
