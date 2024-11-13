package com.example.banggusuk_michelin.Repository;

import com.example.banggusuk_michelin.entity.Group;
import com.example.banggusuk_michelin.entity.User;
import jakarta.persistence.EntityManager;
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

    public Optional<User> findByKeyCode(String keyCode){
        return Optional.ofNullable(em.find(User.class, keyCode));
    }

    public User save(User user){
        em.persist(user);
        return user;
    }
}
