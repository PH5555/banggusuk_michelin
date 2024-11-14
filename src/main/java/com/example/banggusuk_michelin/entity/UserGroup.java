package com.example.banggusuk_michelin.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserGroup {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gruop_id")
    private Group group;

    public UserGroup() {
    }

    public UserGroup(User user, Group group) {
        this.user = user;
        this.group = group;
    }
}
