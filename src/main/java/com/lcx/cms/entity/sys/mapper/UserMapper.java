package com.lcx.cms.entity.sys.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.entity.sys.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author lcx
 * @since 2020-12-07
 */
public interface UserMapper extends BaseMapper<User> {

    List<User> pageAll(Page<User> page, @Param("user") User user);

    User getWithRole(Integer userId);
}