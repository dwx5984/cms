package com.lcx.cms.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@Configuration
@Slf4j
public class WebSecurityConfig implements WebMvcConfigurer {

    /**
     * 登录session key
     */
    public final static String SESSION_KEY = "user";

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

        // 排除配置
        addInterceptor.excludePathPatterns("/class/error");
        addInterceptor.excludePathPatterns("/class/login**");
        addInterceptor.excludePathPatterns("/class/logout");

        // 拦截配置
        addInterceptor.addPathPatterns("/class/**");
    }

    private static class SecurityInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
            String reqPath = httpServletRequest.getServletPath();
            // 只有返回true才会继续向下执行，返回false取消当前请求
            HttpSession session = httpServletRequest.getSession();
            if (session == null || session.getAttribute(SESSION_KEY) == null) {
                log.trace("access#{}, not logged in, return", reqPath);
//                httpServletResponse.sendRedirect("/class/login");
                // 最外层打开登录页
                PrintWriter writer = httpServletResponse.getWriter();
                writer.println("<html><script>window.open ('/class/login','_top')</script></html>");

                return false;
            }
            return true;
        }
    }
}