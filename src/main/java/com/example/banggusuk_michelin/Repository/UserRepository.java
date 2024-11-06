package com.example.banggusuk_michelin.Repository;

import com.example.banggusuk_michelin.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final EntityManager em;

    public UserRepository(EntityManager em) {
        this.em = em;
    }

    public User findByKeyCode(String keyCode){
        return em.find(User.class, keyCode);
    }
}
