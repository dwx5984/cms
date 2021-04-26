package com.lcx.cms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lcx.cms.base.AppException;
import com.lcx.cms.base.Response;
import com.lcx.cms.entity.sys.entity.Role;
import com.lcx.cms.entity.sys.entity.User;
import com.lcx.cms.entity.sys.enums.RoleType;
import com.lcx.cms.entity.sys.service.MenuService;
import com.lcx.cms.entity.sys.service.RoleService;
import com.lcx.cms.entity.sys.service.UserService;
import com.lcx.cms.enums.Bool;
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

    @Autowired
    private RoleService roleService;

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
        query.setStatus(Bool.Yes);
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

    @GetMapping("menu")
    public String menu() {
        return "page/menu";
    }

    @GetMapping("course")
    public String course() {
        return "page/course";
    }

    @GetMapping("mycourse")
    public String myCourse() {
        return "page/mycourse";
    }

    @GetMapping("attendRecord")
    public String attendRecord() {
        return "page/attendrecord";
    }

    @GetMapping("attendWeek")
    public String attendWeek() {
        return "page/attendweek";
    }

    @GetMapping("attendMonth")
    public String attendMonth() {
        return "page/attendmonth";
    }

    @GetMapping("attendCount")
    public String attendCount() {
        return "page/attendcount";
    }

    @GetMapping("attendCompare")
    public String attendCompare() {
        return "page/attendcompare";
    }


    @GetMapping("exchange")
    public String exchange() {
        return "page/exchange";
    }

    @GetMapping("chat")
    public String chat() {
        return "page/chat";
    }

    @GetMapping("weekImport")
    public String weekImport() {
        return "page/weekimport";
    }


    @GetMapping("chatRecord")
    public ModelAndView chatrecord() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("page/chatrecord");
        User withRole = userService.findWithRole(RequestUtil.getCurrentUserId());
        if (!withRole.getRole().getId().equals(300)) {
            mv.addObject("teacher", false);
        } else {
            mv.addObject("teacher", true);
        }
        return mv;
    }


    @GetMapping("attendRecordTotal/{userId}")
    public ModelAndView attendRecordTotal(@PathVariable Integer userId) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("userId", userId);
        mv.setViewName("page/attendrecordTotal");
        return mv;
    }

    @GetMapping("user")
    public ModelAndView user() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("roles", roleService.list(new QueryWrapper<Role>()));
        modelAndView.setViewName("page/user");
        return modelAndView;
    }




}
