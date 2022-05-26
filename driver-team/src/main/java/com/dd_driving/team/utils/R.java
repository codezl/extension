package com.dd_driving.team.utils;

import lombok.Data;

@Data
public class R<T>{

    private Integer code;
    private String msg;
    private T data;

    R(Integer code,String msg,T data) {
       this.code=code;
       this.msg=msg;
       this.data=data;
    }

    R(Integer code,String msg) {
        this.code=code;
        this.msg=msg;
    }

    public static <T> R<T> success(T data) {
        return new R<>(ResInfo.SUCCESS_CODE,ResInfo.SUCCESS_MSG,data);
    }

    public static <T>R<T> fail(T data) {
        return new R<>(ResInfo.FAIL_CODE,ResInfo.FAIL_MSG,data);
    }

    public static <T>R<T> ok() {
        return new R<>(ResInfo.SUCCESS_CODE,ResInfo.SUCCESS_MSG);
    }

    public static <T>R<T> fail() {
        return new R<>(ResInfo.FAIL_CODE,ResInfo.FAIL_MSG,null);
    }
}
