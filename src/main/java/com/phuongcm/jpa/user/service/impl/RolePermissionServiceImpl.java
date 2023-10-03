package com.phuongcm.jpa.user.service.impl;

import com.phuongcm.jpa.user.entity.Permission;
import com.phuongcm.jpa.user.repository.PermissionRepository;
import com.phuongcm.jpa.user.repository.RolePermissionRepository;
import com.phuongcm.jpa.user.service.RolePermissionService;
import com.phuongcm.jpa.user.entity.RolePermisstion;
import com.phuongcm.jpa.user.utils.ResultCode;
import com.phuongcm.jpa.user.utils.ServiceException;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    private final PermissionRepository permissionRepository;

    private final RolePermissionRepository rolePermissionRepository;

    public RolePermissionServiceImpl(PermissionRepository permissionRepository, RolePermissionRepository rolePermissionRepository){
        this.permissionRepository = permissionRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }
    @Override
    public List<String> listTagsByRoleIds(List<Integer> roleIds) {
        List<RolePermisstion> rolePermisstions = rolePermissionRepository.findAllByRoleIdIn(roleIds);
        List<String> tags = rolePermisstions.stream().map(RolePermisstion::getTag).collect(Collectors.toList());
        return tags;
    }

    @Override
    public List<String> listTagsByRoleId(Integer roleId) {
        List<RolePermisstion> rolePermisstions = rolePermissionRepository.findAllByRoleId(roleId);
        List<String> tags = rolePermisstions.stream().map(RolePermisstion::getTag).collect(Collectors.toList());
        List<Permission> permissions = Collections.EMPTY_LIST;
        if(!tags.isEmpty()){
            permissions = permissionRepository.findAllByTagIn(tags);
        }
        List<String> buttonTags = permissions.stream().filter(permission -> "button".equals(permission.getType())).map(Permission::getTag).collect(Collectors.toList());
        return buttonTags;
    }

    @Override
    public Boolean updatePermissionForRoleId(List<Integer> roleIds, Integer roleId, List<String> tags) {
        List<String> userTags = listTagsByRoleIds(roleIds);
        for(String tag : tags){
            if(!userTags.contains(tag)){
                throw new ServiceException(ResultCode.PERMISSION_EXEEDED);
            }
        }
        rolePermissionRepository.deleteAllByRoleId(roleId);
        List<RolePermisstion> pos = tags.stream().map(tag->{
            RolePermisstion po = new RolePermisstion();
            po.setRoleId(roleId);
            po.setTag(tag);
            po.setCreateTime(LocalDateTime.now());
            return po;
        }).collect(Collectors.toList());
        rolePermissionRepository.saveAll(pos);
        redisTemplate.convertAndSend("channel_update_permissions","rolePermissions");
        return true;
    }
}
