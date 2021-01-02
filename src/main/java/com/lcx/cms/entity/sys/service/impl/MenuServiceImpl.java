package com.lcx.cms.entity.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.entity.sys.entity.Menu;
import com.lcx.cms.entity.sys.entity.User;
import com.lcx.cms.entity.sys.enums.RoleType;
import com.lcx.cms.entity.sys.mapper.MenuMapper;
import com.lcx.cms.entity.sys.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcx.cms.entity.sys.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  service实现
 *
 * @author lcx
 * @since 2020-12-07
 */
@Slf4j
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private final UserService userService;

    @Autowired
    public MenuServiceImpl(@Lazy UserService userService) {
        this.userService = userService;
    }


    @Override
    public List<Menu> findMenusByUser(Long userId) {
        User currentUser = userService.findWithRole(userId);
        // 管理员返回所有菜单
        if (currentUser != null
                && currentUser.getRole() != null
                && RoleType.ADMIN.equals(currentUser.getRole().getType())) {
            return baseMapper.pageAll(null, new Menu());
        }
        return baseMapper.findMenusByUser(userId);
    }

    @Override
    public List<Menu> findMenusByRoleId(Long roleId) {
        return baseMapper.findMenusByRoleId(roleId);
    }

    @Override
    public Page<Menu> pageAll(Menu menu) {
        Page<Menu> page = menu.getPage();
        page.setRecords(baseMapper.pageAll(page, menu));
        return page;
    }
}