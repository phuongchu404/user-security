package com.phuongcm.jpa.user.repository;

import com.phuongcm.jpa.user.entity.RolePermisstion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermisstion, Integer> {
}
