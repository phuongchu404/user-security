package com.phuongcm.jpa.user.repository;

import com.phuongcm.jpa.user.entity.RolePermisstion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermisstion, Integer> {
    List<RolePermisstion> findAllByRoleIdIn(List<Integer> roleIds);

    List<RolePermisstion> findAllByRoleId(Integer roleId);
    void deleteAllByRoleId(Integer roleId);

    void deleteAllByTagNotIn(List<String> tags);
    @Query(value = "select rp from RolePermisstion rp " +
            "where rp.roleId= :adminRoleId and rp.tag = :tag ")
    RolePermisstion findByAdminRoleIdAndTag(@Param("adminRoleId") Integer adminRoleId,@Param("tag") String tag);
}
