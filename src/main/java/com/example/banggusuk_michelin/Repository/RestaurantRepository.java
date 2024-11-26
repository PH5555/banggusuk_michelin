package com.example.banggusuk_michelin.Repository;

import com.example.banggusuk_michelin.entity.Group;
import com.example.banggusuk_michelin.entity.Restaurant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;

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

    public List<Restaurant> findInCurrentGroup(Group group, int rating){
        TypedQuery<Restaurant> query = em.createQuery("select r from Restaurant r join r.comments rc " +
                "where r.group = :group group by r having avg(rc.rating) >= :rating", Restaurant.class);
        query.setParameter("group", group);
        query.setParameter("rating", rating);
        return query.getResultList();
    }

}
