package com.lcx.cms.entity.cs.service;

import com.lcx.cms.entity.cs.entity.TeacherCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lcx.cms.enums.Bool;

import java.util.List;

/**
 * 老师课程关联表 service
 *
 * @author lcx
 * @since 2021-01-25
 */
public interface TeacherCourseService extends IService<TeacherCourse> {


    Bool batch(Integer courseId, List<TeacherCourse> teacherCourses);
}
