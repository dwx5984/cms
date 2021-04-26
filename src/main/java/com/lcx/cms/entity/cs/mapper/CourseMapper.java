package com.lcx.cms.entity.cs.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.entity.cs.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lcx.cms.entity.sys.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
*  Mapper 接口
*
* @author lcx
* @since 2021-01-25
*/
public interface CourseMapper extends BaseMapper<Course> {

    List<Course> findWithAttendHoursByIds(Page<Course> page,  @Param("course") Course course, @Param("userId") Integer userId);

    List<User> compareList(@Param("ids") List<String> ids);

    List<Course> findCompareByUser(Integer userId);
}