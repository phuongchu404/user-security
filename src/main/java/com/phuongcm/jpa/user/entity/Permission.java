package com.phuongcm.jpa.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Permission")
@Getter
@Setter
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tag")
    private String tag;

    @Column(name = "type")
    private String type;

    @Column(name = "method")
    private String method;

    @Column(name = "pattern")
    private String pattern;

    @Column(name = "isWhiteList")
    private Boolean isWhiteList;

    @CreationTimestamp
    @Column(name = "createTime")
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name = "updateTime")
    private LocalDateTime updateTime;

}
