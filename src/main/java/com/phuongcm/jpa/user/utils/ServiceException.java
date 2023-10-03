package com.phuongcm.jpa.user.utils;

import lombok.Getter;

public class ServiceException extends RuntimeException{

    @Getter
    private String resultCode;

    public ServiceException(String resultCode, String message){
        super(message);
        this.resultCode =  resultCode;
    }
    public ServiceException(ResultCode resultCode){
        this(resultCode.getCode(), resultCode.getDescription());
    }
}
