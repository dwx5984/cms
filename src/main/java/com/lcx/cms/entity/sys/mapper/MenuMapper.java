package com.lcx.cms.entity.sys.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.entity.sys.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
*  Mapper 接口
*
* @author lcx
* @since 2020-12-07
*/
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> findMenusByUser(Integer userId);

    List<Menu> findMenusByRoleId(Integer roleId);

    List<Menu> findByRoleIdAndParentId(@Param("roleId") Integer roleId, @Param("parentId") Integer parentId);

    List<Menu> pageAll(Page<Menu> page, @Param("menu") Menu menu);
}