package com.lcx.cms.entity.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.base.AppException;
import com.lcx.cms.base.Response;
import com.lcx.cms.entity.sys.entity.User;
import com.lcx.cms.util.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import com.lcx.cms.entity.sys.service.UserService;

import javax.validation.Valid;

/**
 * controller
 *
 * @author lcx
 * @since 2020-12-07
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("page")
    public Response pageAll(@RequestBody User user) {
        return Response.OK(userService.pageAll(user));
    }

    @PostMapping
    public Response save(@RequestBody @Valid User user) {
        return Response.OK(userService.saveWithRole(user));
    }

    @GetMapping("{id}")
    public Response findWithRole(@PathVariable Integer id) {
        return Response.OK(userService.findWithRole(id));
    }

    @GetMapping("cur")
    public Response getCurrent() {
        return Response.OK(userService.findWithRole(RequestUtil.getCurrentUserId()));
    }

    @GetMapping("byRole/{roleId}")
    public Response findByRole(@PathVariable Integer roleId, @RequestParam(required = false) boolean excludeMe) {
        return Response.OK(userService.findByRole(roleId, excludeMe));
    }

    @PostMapping("update")
    public Response updateWithRole(@RequestBody User user) {
        return Response.OK(userService.updateWithRole(user));
    }

    @PutMapping
    public Response update(@RequestBody User user) {
        User byId = userService.getById(user.getId());
        if (StringUtils.isNotBlank(user.getOldPassword())) {
            if (!byId.getPassword().equals(user.getOldPassword())) {
                throw AppException.illegal("旧密码错误");
            }
        }
        User user1 = userService.getOne(new QueryWrapper<User>().eq("mobile", user.getMobile()));
        if (user1 != null) {
            if (!user.getId().equals(user1.getId()))
                throw AppException.mobileExits();
        }
        User user2 = userService.getOne(new QueryWrapper<User>().eq("number", user.getNumber()));
        if (user2 != null) {
            if (!user.getId().equals(user2.getId()))
                throw AppException.numberExits();
        }
        User user3 = userService.getOne(new QueryWrapper<User>().eq("email", user.getEmail()));
        if (user3 != null) {
            if (!user.getId().equals(user3.getId()))
                throw AppException.emailExits();
        }
        return Response.OK(userService.updateById(user));
    }

    @PostMapping("delete/{userId}")
    public Response delete(@PathVariable Integer userId) {
        return Response.OK(userService.removeUser(userId));
    }


    @GetMapping("page")
    public Response page(Long page, Long limit, @RequestParam(required = false) String name
            , @RequestParam(required = false) String number
            , @RequestParam(required = false) String mobile
            , @RequestParam(required = false) String email) {
        User user = new User();
        user.setPage(new Page<>(page, limit));
        user.setName(name);
        user.setNumber(number);
        user.setMobile(mobile);
        user.setEmail(email);
        return Response.OK(userService.pageAll(user));
    }

}
