package com.lcx.cms.entity.cs.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lcx.cms.base.Response;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import com.lcx.cms.base.Response;
import com.lcx.cms.enums.Bool;


import com.lcx.cms.entity.cs.service.TeacherCourseService;
import com.lcx.cms.entity.cs.entity.TeacherCourse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 老师课程关联表 controller
 *
 * @author lcx
 * @since 2021-01-25
 */
@RestController
@RequestMapping("/rest/teacherCourses")
public class TeacherCourseController {

    @Autowired
    private TeacherCourseService teacherCourseService;

    @Autowired
    public TeacherCourseController(TeacherCourseService teacherCourseService) {
        this.teacherCourseService = teacherCourseService;
    }


    /**
     * 查询TeacherCourse
     *
     * @param id
     * @return TeacherCourseDomain
     */
    @GetMapping("/{id}")
    public Response get(@PathVariable Integer id) {
        TeacherCourse teacherCourse = teacherCourseService.getById(id);
        return Response.OK(teacherCourse);
    }

    @GetMapping("/byCourseId/{courseId}")
    public Response findByCourseId(@PathVariable Integer courseId) {
        List<Integer> teacherCourses = teacherCourseService.list(new QueryWrapper<TeacherCourse>().eq("course_id", courseId).eq("status", Bool.Yes.getValue()))
                .stream().map(TeacherCourse::getUserId).collect(Collectors.toList());
        return Response.OK(teacherCourses);
    }


    /**
     * 新增TeacherCourse
     *
     * @param teacherCourse
     * @return TeacherCourseDomain
     */
    @PostMapping
    public Response save(@RequestBody TeacherCourse teacherCourse) {
        teacherCourseService.save(teacherCourse);
        return Response.OK(teacherCourse);
    }

    @PostMapping("batch/{courseId}")
    public Response save(@PathVariable Integer courseId, @RequestBody List<TeacherCourse> teacherCourses) {
        return Response.OK(teacherCourseService.batch(courseId, teacherCourses));
    }

    /**
     * 更新TeacherCourse
     *
     * @param teacherCourse
     * @return TeacherCourseDomain
     */
    @PutMapping("/{id}")
    public Response update(@PathVariable Integer id, @RequestBody TeacherCourse teacherCourse) {
        boolean success = teacherCourseService.updateById(teacherCourse);
        return Response.OK(success ? Bool.Yes : Bool.No);
    }

}
