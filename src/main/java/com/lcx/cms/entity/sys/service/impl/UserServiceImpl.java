package com.lcx.cms.entity.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.base.AppException;
import com.lcx.cms.entity.sys.entity.Role;
import com.lcx.cms.entity.sys.entity.RoleUser;
import com.lcx.cms.entity.sys.entity.User;
import com.lcx.cms.entity.sys.mapper.UserMapper;
import com.lcx.cms.entity.sys.service.RoleService;
import com.lcx.cms.entity.sys.service.RoleUserService;
import com.lcx.cms.entity.sys.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 *  service实现
 *
 * @author lcx
 * @since 2020-12-07
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final RoleUserService roleUserService;

    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(RoleUserService roleUserService, RoleService roleService) {
        this.roleUserService = roleUserService;
        this.roleService = roleService;
    }

    @Override
    public Page<User> pageAll(User user) {
        Page<User> page = user.getPage();
        page.setRecords(baseMapper.pageAll(page, user));
        return page;
    }

    @Override
    public User saveWithRole(User user) {
        save(user);
        Role role = user.getRole();
        if (role != null && role.getId() != null) {
            RoleUser roleUser = new RoleUser();
            roleUser.setRoleId(role.getId());
            roleUser.setUserId(user.getId());
            roleUserService.save(roleUser);
        }
        return user;
    }

    @Override
    public User findWithRole(Long id) {
        User user = Optional.ofNullable(getById(id)).orElseThrow(AppException::dataNotFoundException);

        RoleUser roleUser = new RoleUser();
        roleUser.setUserId(id);
        RoleUser exitsRoleUser = roleUserService.getOne(new QueryWrapper<>(roleUser));
        if (exitsRoleUser != null) {
            user.setRole(roleService.getById(exitsRoleUser.getRoleId()));
        }
        return user;
    }

    @Override
    public User updateWithRole(User user) {
        updateById(user);
        if (user.getRole() != null
                && user.getRole().getId() != null) {
            RoleUser roleUser = new RoleUser();
            roleUser.setUserId(user.getId());
            RoleUser exits = roleUserService.getOne(new QueryWrapper<>(roleUser));
            exits.setRoleId(user.getRole().getId());
            roleUserService.updateById(exits);
        }
        return user;
    }
}