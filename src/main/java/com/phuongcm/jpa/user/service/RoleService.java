package com.phuongcm.jpa.user.service;

import com.phuongcm.jpa.user.entity.Role;
import java.util.List;

public interface RoleService {
    List<Role> listAllRoles();

    Boolean addRole(String roleName, String description);

    Boolean deleteRoleById(int id);

    Boolean updateRoleById(Role record);

    List<Role> findByUserId(int userId);
}
