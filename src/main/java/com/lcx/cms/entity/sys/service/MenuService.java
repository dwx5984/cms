package com.lcx.cms.entity.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.entity.sys.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lcx.cms.entity.sys.entity.Role;

import java.util.List;

/**
 *  service
 *
 * @author lcx
 * @since 2020-12-07
 */
public interface MenuService extends IService<Menu> {


    /**
     * 根据用户查询对应菜单
     * @param userId 用户id
     * @return 菜单列表
     */
    List<Menu> findMenusByUser(Integer userId);

    /**
     * 根据角色查询对应菜单
     * @param roleId 角色id
     * @return 菜单列表
     */
    List<Menu> findMenusByRoleId(Integer roleId);

    /**
     * 分页查询所有菜单
     * @param menu
     * @return
     */
    Page<Menu> pageAll(Menu menu);
}
