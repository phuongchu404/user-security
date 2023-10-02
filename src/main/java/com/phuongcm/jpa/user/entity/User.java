package com.phuongcm.jpa.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userName")
    private String userName;

    @Column(name = "realName")
    private String realName;

    @Column(name = "password")
    private String password;

    @Column(name = "secret")
    private String secret;

    @Column(name = "twoStep")
    private int twoStep;

    @Column(name = "lastLogin")
    private LocalDateTime lastLogin;

    @Column(name = "lastFaultyLogin")
    private LocalDateTime lastFaultyLogin;

    @Column(name = "consPassFaulty")
    private int consPassFaulty;

    @Column(name = "consOptFaulty")
    private int consOptFaulty;

    @Column(name = "createTime")
    private LocalDateTime createTime;

    @Column(name = "updateTime")
    private LocalDateTime updateTime;
}
