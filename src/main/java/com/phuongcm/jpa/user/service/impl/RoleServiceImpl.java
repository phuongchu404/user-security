package com.phuongcm.jpa.user.service.impl;

import com.phuongcm.jpa.user.entity.Role;
import com.phuongcm.jpa.user.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Override
    public List<Role> listAllRoles() {
        return null;
    }

    @Override
    public Boolean addRole(String roleName, String description) {
        return null;
    }

    @Override
    public Boolean deleteRoleById(int id) {
        return null;
    }

    @Override
    public Boolean updateRoleById(Role record) {
        return null;
    }

    @Override
    public List<Role> findByUserId(int userId) {
        return null;
    }
}
