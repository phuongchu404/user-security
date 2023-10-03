package com.phuongcm.jpa.user.service;

import com.phuongcm.jpa.user.entity.Permission;

import java.util.List;

public interface PermissionService {
    Boolean syncPermissions(List<Permission> permissions);
}
