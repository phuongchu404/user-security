package com.phuongcm.jpa.user.repository;

import com.phuongcm.jpa.user.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    List<Permission> findAllByType(String type);

    List<Permission> findAllByTagIn(List<String> tags);

    void deleteAllByTagNotIn(List<String> tags);

    Permission findByTag(String tag);
}
