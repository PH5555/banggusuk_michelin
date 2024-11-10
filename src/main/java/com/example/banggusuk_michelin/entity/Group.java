package com.example.banggusuk_michelin.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "groups")
@Data
@Table(name = "groups")
public class Group {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String groupId;

    @Column(unique = true, length = 30, nullable = false)
    private String groupName;

    @Column(length = 100, nullable = false)
    private String password;

    @Column()
    private String image;

    public Group() {
    }

    public Group(String groupName, String password, String image) {
        this.groupName = groupName;
        this.password = password;
        this.image = image;
    }
}
