package com.itisacat.backend.demo.utils;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {

    private int code;
    private String desc;

    public BusinessException(int code, String desc) {
        super("BusinessException -> code : " + code + ", desc : " + desc);
        this.code = code;
        this.desc = desc;
    }

}
