package com.phuongcm.jpa.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "role")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "roleName")
    private String roleName;

    @Column(name = "description")
    private String description;

    @Column(name = "sortno")
    private int sortNo;

    @Column(name = "createUserId")
    private int createUserId;

    @Column(name = "crateTime")
    private LocalDateTime createTime;

    @Column(name = "updateTime")
    private LocalDateTime updateTime;

}
