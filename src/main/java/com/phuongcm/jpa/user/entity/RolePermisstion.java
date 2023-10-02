package com.phuongcm.jpa.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "role_permission")
@Getter
@Setter
public class RolePermisstion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "roleId")
    private int roleId;

    @Column(name = "tag")
    private String tag;

    @Column(name = "createTime")
    private LocalDateTime createTime;
}
