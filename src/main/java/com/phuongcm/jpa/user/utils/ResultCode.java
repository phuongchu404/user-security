package com.phuongcm.jpa.user.utils;

public enum ResultCode {
    USER_DOES_NOT_EXISTS("01", "user does not exists"),
    SUCCESS("100","SUCCESS"),
    PERMISSION_EXEEDED("229","Exeeded current user's permissions")
    ;

    private String code;
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    ResultCode(String code, String description){
        this.code = code;
        this.description = description;
    }
}
