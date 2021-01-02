package com.lcx.cms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lcx.cms.base.AppException;
import com.lcx.cms.base.Response;
import com.lcx.cms.entity.sys.entity.User;
import com.lcx.cms.entity.sys.service.MenuService;
import com.lcx.cms.entity.sys.service.UserService;
import com.lcx.cms.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.lcx.cms.config.WebSecurityConfig.SESSION_KEY;

@Controller
@RequestMapping(path = "class")
@Slf4j
public class IndexController {


    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @RequestMapping(path = "admin", method = RequestMethod.GET)
    public ModelAndView admin() {
        ModelAndView model = new ModelAndView();
        model.setViewName("page/admin");
        model.addObject("menus", menuService.findMenusByUser(RequestUtil.getCurrentUserId()));
        return model;
//        return "page/admin";
    }

    @RequestMapping(path = "login", method = RequestMethod.GET)
    public String login() {
        return "page/login";
    }


    @PostMapping("login")
    @ResponseBody
    public Response login(@RequestBody User user) {
        String loginName = user.getLoginName();
        User query = new User();
        if (StringUtils.contains(loginName, "@")) {
            query.setEmail(loginName);
        } else {
            query.setMobile(loginName);
        }
        // 根据手机号或邮箱查询用户
        User exits = Optional.ofNullable(userService.getOne(new QueryWrapper<>(query)))
                .orElseThrow(() -> AppException.newException("用户不存在"));
        if (!user.getPassword().equals(exits.getPassword())) {
            throw AppException.incorrectPasswordException();
        }
        // 登录成功
        HttpSession session = RequestUtil.getSession();
        session.setAttribute(SESSION_KEY, exits.getId());
        return Response.OK("登录成功", user);
    }

    /**
     * 注销
     * @return
     */
    @GetMapping("logout")
    @ResponseBody
    public Response logout() {
        // 删除session
        HttpSession session = RequestUtil.getSession();
        session.invalidate();
        return Response.OK("注销成功", null);
    }



    @GetMapping("role")
    public String role() {
        return "page/role";
    }



}
