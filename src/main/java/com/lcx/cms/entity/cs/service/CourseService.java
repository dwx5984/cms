package com.lcx.cms.entity.cs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.entity.cs.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lcx.cms.entity.sys.entity.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  service
 *
 * @author lcx
 * @since 2021-01-25
 */
public interface CourseService extends IService<Course> {


    Page<Course> findWithAttendHoursByUser(Course course, Integer userId);

    List<User> compare(String ids);

    Page<User> checkCompare(User user, Date start, Date end, String type);
}
