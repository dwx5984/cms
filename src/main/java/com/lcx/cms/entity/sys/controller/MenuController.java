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
    public Response get(@PathVariable Integer id) {
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
    @ResponseBody
    public Response page(Long page, Long limit, @RequestParam(required = false) Integer parentId) {
        Menu menu = new Menu();
        menu.setParentId(parentId);
        menu.setPage(new Page<>(page,limit));
        return Response.OK(menuService.pageAll(menu));
    }


    /**
     * 更新Menu
     *
     * @param menu
     * @return MenuDomain
     */
    @PutMapping("/{id}")
    @ResponseBody
    public Response update(@PathVariable Integer id, @RequestBody Menu menu) {
        menu.setId(id);
        boolean success = menuService.updateById(menu);
        return Response.OK(success ? Bool.Yes : Bool.No);
    }

    @PostMapping("delete/{id}")
    @ResponseBody
    public Response delete(@PathVariable Long id) {
        return Response.OK(menuService.removeById(id));
    }

}
