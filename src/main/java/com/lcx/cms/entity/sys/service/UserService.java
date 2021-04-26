package com.lcx.cms.entity.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.entity.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  service
 *
 * @author lcx
 * @since 2020-12-07
 */
public interface UserService extends IService<User> {

    Page<User> pageAll(User user);

    /**
     * 新增用户同时配置角色
     * @param user
     * @return
     */
    User saveWithRole(User user);

    /**
     * 查询用户包含角色
     * @param id
     * @return
     */
    User findWithRole(Integer id);

    User updateWithRole(User user);

    Boolean removeUser(Integer userId);

    List<User> findByRole(Integer roleId, boolean excludeMe);
}

