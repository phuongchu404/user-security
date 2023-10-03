package com.phuongcm.jpa.user.service;

import java.util.List;

public interface RolePermissionService {
    List<String> listTagsByRoleIds(List<Integer> roleIds);

    List<String> listTagsByRoleId(Integer roleId);

    Boolean updatePermissionForRoleId(List<Integer> roleIds, Integer roleId, List<String> tags);
}
