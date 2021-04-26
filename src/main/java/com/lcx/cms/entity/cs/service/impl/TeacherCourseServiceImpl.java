package com.lcx.cms.entity.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lcx.cms.entity.cs.entity.TeacherCourse;
import com.lcx.cms.entity.cs.mapper.TeacherCourseMapper;
import com.lcx.cms.entity.cs.service.TeacherCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcx.cms.enums.Bool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 老师课程关联表 service实现
 *
 * @author lcx
 * @since 2021-01-25
 */
@Slf4j
@Service
public class TeacherCourseServiceImpl extends ServiceImpl<TeacherCourseMapper, TeacherCourse> implements TeacherCourseService {

    @Override
    public Bool batch(Integer courseId, List<TeacherCourse> teacherCourses) {
        this.remove(new QueryWrapper<TeacherCourse>().eq("course_id", courseId));
        if (CollectionUtils.isNotEmpty(teacherCourses)) {
            teacherCourses.forEach(i -> {i.setCourseId(courseId);i.setStatus(Bool.Yes.getValue());});
            this.saveBatch(teacherCourses);
        }
        return Bool.Yes;
    }
}