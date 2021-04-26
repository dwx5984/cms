package com.lcx.cms.entity.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.base.Response;
import com.lcx.cms.entity.sys.vo.LayTreeMenuVO;
import com.lcx.cms.enums.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import com.lcx.cms.entity.sys.service.RoleService;
import com.lcx.cms.entity.sys.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * 用户角色表 controller
 *
 * @author lcx
 * @since 2020-12-07
 */
@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;


//    /**
//     * 查询Role
//     *
//     * @param id
//     * @return Role
//     */
//    @GetMapping("/{id}")
//    public Response get(@PathVariable Long id) {
//        Role role = roleService.getById(id);
//        return Response.OK(role);
//    }

    /**
     * 查询Role以及对应菜单
     * @param id
     * @return Role
     */
    @GetMapping("/{id}")
    public Response findWithMenus(@PathVariable Integer id) {
        return Response.OK(roleService.findWithMenus(id));
    }

    /**
     * 新增Role
     *
     * @param role 要保存的领域对象
     * @return Role
     */
    @PostMapping
    public Response save(@RequestBody Role role) {
        roleService.save(role);
        return Response.OK(role);
    }

    @PostMapping("delete/{id}")
    public Response delete(@PathVariable Integer id) {
        return Response.OK(roleService.removeById(id));
    }

    /**
     * 更新Role
     *
     * @param role 要保存的领域对象
     * @return Role
     */
    @PutMapping("{id}")
    public Response updateWithMenu(@PathVariable Integer id, @RequestBody Role role) {
        role.setId(id);
        return Response.OK(roleService.updateWithMenu(role));
    }


    @GetMapping("list/all")
    public Response findAll() {
        return Response.OK(roleService.list());
    }

    @GetMapping("page")
    public Response page(Long page, Long limit) {
        Role role = new Role();
        role.setPage(new Page<>(page, limit));
        return Response.OK(roleService.pageRole(role));
    }

    @GetMapping("findPermission/{roleId}")
    public List<LayTreeMenuVO> findPermission(@PathVariable Integer roleId) {
        return roleService.findPermission(roleId);
    }

    @PostMapping("updatePermission/{roleId}")
    public Response updatePermission(@RequestBody List<LayTreeMenuVO> layTreeMenuVOs, @PathVariable Integer roleId) {
        return Response.OK(roleService.updatePermission(roleId, layTreeMenuVOs));
    }


}
