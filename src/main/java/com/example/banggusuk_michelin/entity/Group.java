package com.example.banggusuk_michelin.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Group {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String groupId;

    @Column(unique = true, length = 30, nullable = false)
    private String groupName;

    @Column(length = 20, nullable = false)
    private String password;

    @Column()
    private String image;
}
