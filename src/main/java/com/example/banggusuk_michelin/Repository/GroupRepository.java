package com.example.banggusuk_michelin.Repository;

import com.example.banggusuk_michelin.entity.Group;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class GroupRepository {
    private final EntityManager em;

    public GroupRepository(EntityManager em) {
        this.em = em;
    }
    public Optional<Group> findByGroupName(String groupName) {
        TypedQuery<Group> query = em.createQuery("select g from Group g where group_name = :groupName", Group.class);
        query.setParameter("groupName", groupName);
        return query.getResultList().stream().findAny();
    }

    public Group save(Group group){
        em.persist(group);
        return group;
    }
}
