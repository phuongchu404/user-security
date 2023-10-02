package com.phuongcm.jpa.user.cache;

import lombok.Getter;
import org.springframework.util.AntPathMatcher;
import org.apache.commons.codec.binary.StringUtils;
import java.util.Objects;

public class PermissionPattern {
    @Getter
    private String method;

    @Getter
    private String pattern;

    @Getter
    private Boolean whiteList;

    private final AntPathMatcher antPathMatcher;

    public PermissionPattern(String method, String pattern, Boolean whiteList){
        this.method = method;
        this.pattern= pattern;
        this.whiteList = whiteList;
        this.antPathMatcher = new AntPathMatcher();
    }

    public boolean match(String method, String url){
        boolean urlMatch = antPathMatcher.match(pattern, url);
        //nếu không match
        if(!urlMatch){
            return false;
        }
        //Nếu url nằm trong whitelist
        if(whiteList){
            return true;
        }
        //Kiểm tra match method
        boolean methodMatch = this.method == null || this.method.equalsIgnoreCase(method);
        if(methodMatch){
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }
        if(!(obj instanceof PermissionPattern)){
            return false;
        }
        PermissionPattern permissionPattern = (PermissionPattern) obj;
        if(StringUtils.equals(this.pattern, permissionPattern.getPattern())
                && StringUtils.equals(this.method, permissionPattern.getMethod())
                && this.whiteList.equals(permissionPattern.getWhiteList())){
            return true;
        }
        return false;
    }
}
