package com.lcx.cms.entity.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.base.AppException;
import com.lcx.cms.entity.sys.entity.Menu;
import com.lcx.cms.entity.sys.entity.Role;
import com.lcx.cms.entity.sys.entity.RoleMenu;
import com.lcx.cms.entity.sys.mapper.RoleMapper;
import com.lcx.cms.entity.sys.service.MenuService;
import com.lcx.cms.entity.sys.service.RoleMenuService;
import com.lcx.cms.entity.sys.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcx.cms.entity.sys.vo.LayTreeMenuVO;
import com.lcx.cms.enums.Bool;
import com.lcx.cms.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 用户角色表 service实现
 *
 * @author lcx
 * @since 2020-12-07
 */
@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleMenuService roleMenuService;

    private final MenuService menuService;

    @Autowired
    public RoleServiceImpl(RoleMenuService roleMenuService, MenuService menuService) {
        this.roleMenuService = roleMenuService;
        this.menuService = menuService;
    }

    @Override
    public Role updateWithMenu(Role role) {
        updateById(role);
        List<RoleMenu> roleMenus = role.getRoleMenus();
        if (CollectionUtils.isNotEmpty(roleMenus)) {
            // 把原来的权限删掉
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(role.getId());
            roleMenuService.remove(new QueryWrapper<>(roleMenu));

            // 重新生成新的权限
            roleMenuService.saveBatch(roleMenus);
        }
        return role;
    }

    @Override
    public Role findWithMenus(Long id) {
        Role role = Optional.ofNullable(getById(id)).orElseThrow(AppException::dataNotFoundException);
        role.setMenus(menuService.findMenusByRoleId(id));
        return role;
    }

    @Override
    public Page<Role> pageRole(Role role) {
        Page<Role> page = role.getPage();
        return page(page);
    }

    @Override
    public List<LayTreeMenuVO> findPermission(Long roleId) {
//        Role byUserId = baseMapper.findByUserId(RequestUtil.getCurrentUserId());
//        boolean flag = "管理员".equals(byUserId.getName());
        List<LayTreeMenuVO> rootMenus = baseMapper.findPermission(roleId, null);
        for (LayTreeMenuVO lv2 : rootMenus) {
//            if (flag && "系统设置".equals(lv2.getTitle())) {
//                lv2.set
//            }
            lv2.setChildren(baseMapper.findPermission(roleId, Long.valueOf(lv2.getId())));
        }
        return rootMenus;
    }

    @Override
    public Bool updatePermission(Long roleId, List<LayTreeMenuVO> layTreeMenuVOs) {
        // 删除全部权限
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setRoleId(roleId);
        roleMenuService.remove(new QueryWrapper<>(roleMenu));
        // 重新生成权限
        if (CollectionUtils.isNotEmpty(layTreeMenuVOs)) {
            List<RoleMenu> roleMenus = new ArrayList<>();
            for (LayTreeMenuVO layTreeMenuVO : layTreeMenuVOs) {
                RoleMenu insertEntity = new RoleMenu();
                insertEntity.setRoleId(roleId);
                insertEntity.setMenuId(Long.valueOf(layTreeMenuVO.getId()));
                roleMenus.add(insertEntity);
                if (CollectionUtils.isNotEmpty(layTreeMenuVO.getChildren())) {
                    for (LayTreeMenuVO vo : layTreeMenuVO.getChildren()) {
                        RoleMenu insertEntity1 = new RoleMenu();
                        insertEntity1.setRoleId(roleId);
                        insertEntity1.setMenuId(Long.valueOf(vo.getId()));
                        roleMenus.add(insertEntity1);
                    }
                }
            }
            roleMenuService.saveBatch(roleMenus);
        }
        return Bool.Yes;
    }
}