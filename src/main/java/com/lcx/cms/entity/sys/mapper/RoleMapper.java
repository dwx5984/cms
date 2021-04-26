package com.lcx.cms.entity.sys.mapper;

import com.lcx.cms.entity.sys.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lcx.cms.entity.sys.vo.LayTreeMenuVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* 用户角色表 Mapper 接口
*
* @author lcx
* @since 2020-12-07
*/
public interface RoleMapper extends BaseMapper<Role> {

    List<LayTreeMenuVO> findPermission(@Param("roleId") Integer roleId, @Param("parentId") Integer parentId);

    Role findByUserId(Integer userId);
}