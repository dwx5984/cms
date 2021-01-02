package com.lcx.cms.entity.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.entity.sys.entity.Menu;
import com.lcx.cms.entity.sys.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lcx.cms.entity.sys.vo.LayTreeMenuVO;
import com.lcx.cms.enums.Bool;

import java.util.List;
import java.util.Map;

/**
 * 用户角色表 service
 *
 * @author lcx
 * @since 2020-12-07
 */
public interface RoleService extends IService<Role> {


    /**
     * 更新role以及对应的权限菜单
     * @param role
     * @return
     */
    Role updateWithMenu(Role role);

    /**
     * 查询Role以及对应菜单
     * @param id
     * @return Role
     */
    Role findWithMenus(Long id);

    /**
     * 分页
     * @param role
     * @return
     */
    Page<Role> pageRole(Role role);

    List<LayTreeMenuVO> findPermission(Long roleId);

    Bool updatePermission(Long roleId, List<LayTreeMenuVO> layTreeMenuVOs);
}
