package com.lcx.cms.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AppException extends RuntimeException {

    /**
     * 错误码
     */
    private String code;
    /**
     * 消息
     */
    private String tips;


    public AppException(String code, String tips) {
        this.code = code;
        this.tips = tips;
    }

    public static AppException dataNotFoundException() {
        return new AppException(Response.FAIL, "数据未找到");
    }

    public static AppException incorrectPasswordException() {
        return new AppException(Response.FAIL, "密码不正确");
    }

    public static AppException newException(String msg) {
        return new AppException(Response.FAIL, msg);
    }

    /**
     * TODO 跳转到登录页
     * @return
     */
    public static AppException unAuthException() {
        return new AppException(Response.FAIL, "用户未登录");
    }
    public static AppException mobileExits() {
        return new AppException(Response.FAIL, "手机号已被使用");
    }

    public static AppException numberExits() {
        return new AppException(Response.FAIL, "工号已被使用");
    }

    public static AppException illegal(String msg) {
        return new AppException(Response.FAIL, msg);
    }

    public static AppException emailExits() {
        return new AppException(Response.FAIL, "邮箱已被使用");
    }
}
