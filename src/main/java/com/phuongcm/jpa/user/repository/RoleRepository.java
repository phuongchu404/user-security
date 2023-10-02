package com.phuongcm.jpa.user.repository;

import com.phuongcm.jpa.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.relation.RoleInfo;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
