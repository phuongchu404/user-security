package com.phuongcm.jpa.user.cache;

import com.phuongcm.jpa.user.entity.Permission;
import com.phuongcm.jpa.user.entity.RolePermisstion;
import com.phuongcm.jpa.user.repository.PermissionRepository;
import com.phuongcm.jpa.user.repository.RolePermissionRepository;
import com.phuongcm.jpa.user.service.PermissionCheckerService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class PermissionCache implements MessageListener, PermissionCheckerService {
    private final RolePermissionRepository rolePermissionRepository;

    private final PermissionRepository permissionRepository;

    private Map<String, List<PermissionPattern>> permissionCache = new HashMap<>();

    public PermissionCache(RolePermissionRepository rolePermissionRepository, PermissionRepository permissionRepository){
        this.rolePermissionRepository = rolePermissionRepository;
        this.permissionRepository = permissionRepository;
    }

    private Map<String, List<PermissionPattern>> buildPermissionCache(){
        //chỉ lấy kiểu button
        List<Permission> permissions = permissionRepository.findAllByType("button");

        //lấy tất cả role-permission
        List<RolePermisstion> rolePermisstions = rolePermissionRepository.findAll();

        Map<String, PermissionPattern> tagPatterns = new HashMap<>();
        for(Permission permission: permissions){
            String tag = permission.getTag();
            PermissionPattern permissionPattern = new PermissionPattern(permission.getMethod(), permission.getPattern(), permission.getIsWhiteList());
            if(tagPatterns.containsKey(tag)) {
                log.error("Duplicate permission tag. Tag: {}", tag);
            }else{
                tagPatterns.put(tag, permissionPattern);
            }
        }

        Map<String, List<PermissionPattern>> rolePatterns = new HashMap<>();
        for(RolePermisstion rolePermisstion : rolePermisstions){
            String tag= rolePermisstion.getTag();
            if(!tagPatterns.containsKey(tag)){

            }else{
                String role = String.valueOf(rolePermisstion.getRoleId());
                if(rolePatterns.containsKey(role)){
                    rolePatterns.get(role).add(tagPatterns.get(tag));
                }else{
                    List<PermissionPattern> patterns = new ArrayList<>();
                    patterns.add(tagPatterns.get(tag));
                    rolePatterns.put(role, patterns);
                }
            }
        }
        return rolePatterns;
    }
    @PostConstruct
    private void initPermissionCache(){
        permissionCache = buildPermissionCache();
    }
    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("Received redis published message. Channel: {}, Body: {}", new String(message.getChannel()), new String(message.getBody()));
        permissionCache = buildPermissionCache();
    }

    @Override
    public boolean checkPermission(String method, String url, String roleIdString, List<Integer> roleIds) {
        List<PermissionPattern> patterns = permissionCache.get(roleIdString);
        if(patterns==null){
            patterns = new ArrayList<>();
            for(Integer roleId:roleIds){
                String role = roleId.toString();
                if(permissionCache.containsKey(role)){
                    patterns = Stream.of(patterns, permissionCache.get(role)).flatMap(Collection::stream).distinct().collect(Collectors.toList());
                }
            }
            permissionCache.put(roleIdString, patterns);
        }
        for(PermissionPattern pattern:patterns){
            if(pattern.match(method, url)){
                return true;
            }
        }
        return false;
    }
}
