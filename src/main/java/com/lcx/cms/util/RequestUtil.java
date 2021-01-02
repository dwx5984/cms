package com.lcx.cms.util;

import com.lcx.cms.base.AppException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.lcx.cms.config.WebSecurityConfig.SESSION_KEY;

public class RequestUtil {

    private static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取到Request对象
        assert attrs != null;
        return attrs.getRequest();
    }

    /**
     * 获取http session
     * @return
     */
    public static HttpSession getSession() {
        return Optional.ofNullable(getRequest().getSession()).orElseThrow(AppException::unAuthException);
    }

    /**
     * 获取当前用户id
     * @return
     */
    public static Long getCurrentUserId() {
        return (Long) Optional.ofNullable(getSession().getAttribute(SESSION_KEY)).orElseThrow(AppException::unAuthException);
    }
}
