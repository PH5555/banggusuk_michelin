package com.example.banggusuk_michelin.Repository;

import com.example.banggusuk_michelin.entity.Group;
import com.example.banggusuk_michelin.entity.User;
import com.example.banggusuk_michelin.entity.UserGroup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserGroupRepository {
    private final EntityManager em;

    public UserGroupRepository(EntityManager em) {
        this.em = em;
    }

    public UserGroup save(User user, Group group){
        UserGroup userGroup = new UserGroup(user, group);
        em.persist(userGroup);
        return userGroup;
    }

    //TODO: need test
    public boolean findGroupInUser(User user, Group group){
        Query query = em.createQuery("select ug from user_group ug where groups = :group and user = :user");
        query.setParameter("user", user);
        query.setParameter("group", group);

        return !query.getResultList().isEmpty();
    }
}
