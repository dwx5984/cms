package com.lcx.cms.entity.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.base.Response;
import com.lcx.cms.entity.sys.entity.Role;
import com.lcx.cms.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.lcx.cms.enums.Bool;


import com.lcx.cms.entity.sys.service.MenuService;
import com.lcx.cms.entity.sys.entity.Menu;
import org.springframework.web.servlet.ModelAndView;

/**
 * controller
 *
 * @author lcx
 * @since 2020-12-07
 */
@Controller
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;


    /**
     * 查询Menu
     *
     * @param id
     * @return MenuDomain
     */
    @GetMapping("/{id}")
    @ResponseBody
    public Response get(@PathVariable Long id) {
        Menu menu = menuService.getById(id);
        return Response.OK(menu);
    }

    /**
     * 查询Menu
     * @return MenuDomain
     */
    @GetMapping("/all")
    public ModelAndView getAll() {
        ModelAndView model = new ModelAndView();
        model.setViewName("page/admin");
        model.addObject("menus", menuService.findMenusByUser(RequestUtil.getCurrentUserId()));
        return model;
    }

    /**
     * 分页查询管理Menu
     * @return MenuDomain
     */
    @GetMapping("/page")
    public Response page(Long page, Long limit, @RequestParam(required = false) String name) {
        Menu menu = new Menu();
        menu.setName(name);
        return Response.OK(menuService.pageAll(menu));
    }





//    /**
//     * 新增Menu
//     *
//     * @param menu
//     * @return MenuDomain
//     */
//    @PostMapping
//    @ResponseBody
//    public Response save(@RequestBody Menu menu) {
//        // TODO 同时把菜单分配给管理员
//        menuService.save(menu);
//        return Response.OK(menu);
//    }

    /**
     * 更新Menu
     *
     * @param menu
     * @return MenuDomain
     */
    @PutMapping("/{id}")
    @ResponseBody
    public Response update(@PathVariable Long id, @RequestBody Menu menu) {
        boolean success = menuService.updateById(menu);
        return Response.OK(success ? Bool.Yes : Bool.No);
    }

}
