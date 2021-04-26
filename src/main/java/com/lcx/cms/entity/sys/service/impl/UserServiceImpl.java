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
import com.lcx.cms.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        if (baseMapper.selectOne(new QueryWrapper<User>().eq("mobile", user.getMobile())) != null) {
            throw AppException.mobileExits();
        }
        if (baseMapper.selectOne(new QueryWrapper<User>().eq("number", user.getNumber())) != null) {
            throw AppException.numberExits();
        }
        if (baseMapper.selectOne(new QueryWrapper<User>().eq("email", user.getEmail())) != null) {
            throw AppException.emailExits();
        }
        save(user);
        if (user.getRoleId() != null) {
            RoleUser roleUser = new RoleUser();
            roleUser.setRoleId(user.getRoleId());
            roleUser.setUserId(user.getId());
            roleUserService.save(roleUser);
        }
        return user;
    }
    
    @Override
    public User findWithRole(Integer id) {
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
        User user1 = baseMapper.selectOne(new QueryWrapper<User>().eq("mobile", user.getMobile()));
        if (user1 != null) {
            if (!user.getId().equals(user1.getId()))
                throw AppException.mobileExits();
        }
        User user2 = baseMapper.selectOne(new QueryWrapper<User>().eq("number", user.getNumber()));
        if (user2 != null) {
            if (!user.getId().equals(user2.getId()))
                throw AppException.numberExits();
        }
        User user3 = baseMapper.selectOne(new QueryWrapper<User>().eq("email", user.getEmail()));
        if (user3 != null) {
            if (!user.getId().equals(user3.getId()))
                throw AppException.emailExits();
        }
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

    @Override
    public Boolean removeUser(Integer userId) {
        roleUserService.remove(new QueryWrapper<RoleUser>().eq("user_id", userId));
        return baseMapper.deleteById(userId) > 0;
    }

    @Override
    public List<User> findByRole(Integer roleId, boolean excludeMe) {
        List<RoleUser> list = roleUserService.list(new QueryWrapper<RoleUser>().eq("role_id", roleId));
        Set<Integer> collect = list.stream().map(RoleUser::getUserId).collect(Collectors.toSet());
        if (excludeMe) {
            collect.removeIf(i -> i.equals(RequestUtil.getCurrentUserId()));
        }
        List<User> users = list(new QueryWrapper<User>().in("id", collect));
        return users;
    }
}