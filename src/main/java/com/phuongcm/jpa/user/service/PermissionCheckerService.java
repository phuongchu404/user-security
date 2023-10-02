package com.phuongcm.jpa.user.service;

import java.util.List;

public interface PermissionCheckerService {
    boolean checkPermission(String method, String url, String roleIdString, List<Integer> roleIds);
}
