package com.lcx.cms.base;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * HTTP 返回结果
 */
@AllArgsConstructor
@Data
public class Response {

    public static final String OK = "OK";

    public static final String FAIL = "FAIL";

    /**
     * 响应码
     */
    private String code;
    /**
     * 消息
     */
    private String message;
    /**
     * 具体对象
     */
    private Object data;

    /**
     * 成功返回
     * @param data
     * @return
     */
    public static Response OK(Object data) {
        return new Response("OK", "请求成功", data);
    }

    /**
     * 成功返回
     * @param data
     * @return
     */
    public static Response OK(String msg, Object data) {
        return new Response("OK", msg, data);
    }

    /**
     * 失败返回
     * @param data
     * @return
     */
    public static Response FAIL(Object data) {
        return new Response("fail", "请求失败", data);
    }

    /**
     * 失败返回
     * @param data
     * @return
     */
    public static Response FAIL(String msg, Object data) {
        return new Response("fail", msg, data);
    }
}
