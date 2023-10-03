package com.phuongcm.jpa.user.service;

import com.phuongcm.jpa.user.entity.User;

public interface UserCacheService {
    void storeUser(String userName, User user);

    User loadUser(String userName);

    void removeUser(String userName);

    void publishPermissionUpdate(String message);
}
