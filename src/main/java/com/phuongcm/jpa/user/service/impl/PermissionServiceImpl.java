package com.phuongcm.jpa.user.service.impl;

import com.phuongcm.jpa.user.entity.Permission;
import com.phuongcm.jpa.user.entity.Role;
import com.phuongcm.jpa.user.entity.RolePermisstion;
import com.phuongcm.jpa.user.repository.PermissionRepository;
import com.phuongcm.jpa.user.repository.RolePermissionRepository;
import com.phuongcm.jpa.user.service.PermissionService;
import com.phuongcm.jpa.user.service.RolePermissionService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    private final PermissionRepository permissionRepository;

    private final RolePermissionRepository rolePermissionRepository;

    public PermissionServiceImpl( PermissionRepository permissionRepository, RolePermissionRepository rolePermissionRepository){
        this.permissionRepository = permissionRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }
    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Boolean syncPermissions(List<Permission> permissions) {
        List<String> tags = permissions.stream().map(Permission::getTag).collect(Collectors.toList());

        //1. Xóa hết permissions mà không chứa list tags
        permissionRepository.deleteAllByTagNotIn(tags);

        // THêm hoặc cập nhật permission trong database
        permissions.forEach(permission -> {
            Permission one = permissionRepository.findByTag(permission.getTag());
            if(one!=null){
                one.setType(permission.getType());
                one.setMethod(permission.getMethod());
                one.setPattern(permission.getPattern());
                one.setIsWhiteList(permission.getIsWhiteList());
                permissionRepository.save(one);
            }else{
                permissionRepository.save(permission);
            }
        });

        //3. Xóa các permission mà gán với role nhưng không tồn tại trong list tags
        rolePermissionRepository.deleteAllByTagNotIn(tags);

        //4. Gán tất cả permission cho role admin
        Integer adminRoleId = 1;
        LocalDateTime now =LocalDateTime.now();
        for (Permission permission : permissions) {
            RolePermisstion one = rolePermissionRepository.findByAdminRoleIdAndTag(adminRoleId, permission.getTag());
            if(one == null){
                RolePermisstion rolePermisstion = new RolePermisstion();
                rolePermisstion.setRoleId(adminRoleId);
                rolePermisstion.setTag(permission.getTag());
                rolePermissionRepository.save(rolePermisstion);
            }
        }
        redisTemplate.convertAndSend("channel_update_permissions", "permissions");
        return true;
    }
}
