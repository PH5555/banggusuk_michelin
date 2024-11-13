package com.example.banggusuk_michelin.Repository;

import com.example.banggusuk_michelin.entity.Group;
import com.example.banggusuk_michelin.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {
    private final EntityManager em;

    public UserRepository(EntityManager em) {
        this.em = em;
    }

    public Optional<User> findByKeyCode(String keyCode) {
        TypedQuery<User> query = em.createQuery("select u from User u where keyCode = :keyCode", User.class);
        query.setParameter("keyCode", keyCode);
        return query.getResultList().stream().findAny();
    }

    public User save(User user){
        em.persist(user);
        return user;
    }
}
