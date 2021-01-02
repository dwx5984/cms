package com.lcx.cms.entity.sys.controller;

import com.lcx.cms.base.Response;
import com.lcx.cms.entity.sys.entity.User;
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
    public Response findWithRole(@PathVariable Long id) {
        return Response.OK(userService.findWithRole(id));
    }

    @PostMapping("update")
    public Response updateWithRole(@RequestBody User user) {
        return Response.OK(userService.updateWithRole(user));
    }

}
