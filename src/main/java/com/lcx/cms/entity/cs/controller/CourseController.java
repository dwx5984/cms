package com.lcx.cms.entity.cs.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.base.DateUtil;
import com.lcx.cms.entity.cs.service.TeacherCourseService;
import com.lcx.cms.entity.sys.entity.User;
import com.lcx.cms.util.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lcx.cms.base.Response;
import org.springframework.web.bind.annotation.RequestBody;
import com.lcx.cms.enums.Bool;


import com.lcx.cms.entity.cs.service.CourseService;
import com.lcx.cms.entity.cs.entity.Course;

import java.util.Date;
import java.util.List;

/**
 * controller
 *
 * @author lcx
 * @since 2021-01-25
 */
@RestController
@RequestMapping("/rest/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherCourseService teacherCourseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    /**
     * 查询Course
     *
     * @param id
     * @return CourseDomain
     */
    @GetMapping("/{id}")
    public Response get(@PathVariable Integer id) {
        Course course = courseService.getById(id);
        return Response.OK(course);
    }


    /**
     * 新增Course
     *
     * @param course
     * @return CourseDomain
     */
    @PostMapping
    public Response save(@RequestBody Course course) {
        courseService.save(course);
        return Response.OK(course);
    }

    /**
     * 更新Course
     *
     * @param course
     * @return CourseDomain
     */
    @PutMapping("/{id}")
    public Response update(@PathVariable Integer id, @RequestBody Course course) {
        course.setId(id);
        boolean success = courseService.updateById(course);
        return Response.OK(success ? Bool.Yes : Bool.No);
    }

    /**
     * 分页Course
     *
     * @return CourseDomain
     */
    @GetMapping("page")
    public Response page(Long page, Long limit, @RequestParam(required = false) String name) {
        QueryWrapper<Course> objectQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            objectQueryWrapper.like("name", name);
        }
        Page<Course> objectPage = new Page<>(page, limit);
        Page<Course> pageR = courseService.page(objectPage, objectQueryWrapper);
        return Response.OK(pageR);
    }

    @GetMapping("pageByUser")
    public Response pageByUser(Long page, Long limit, @RequestParam(required = false) String name
            , @RequestParam(required = false) Integer userId) {
        Integer currentUserId = RequestUtil.getCurrentUserId();
        Course build = new Course();
        build.setName(name);
        build.setPage(new Page<>(page, limit));
        Page<Course> courses = courseService.findWithAttendHoursByUser(build, userId == null ? currentUserId : userId);
        return Response.OK(courses);
    }

    @PostMapping("delete/{id}")
    public Response delete(@PathVariable Integer id) {
        return Response.OK(courseService.removeById(id));
    }


    @GetMapping("compare")
    public Response delete(@RequestParam(value = "ids") String ids) {

        return Response.OK(courseService.compare(ids));
    }


    @GetMapping("checkCompare")
    public Response checkCompare(Long page, Long limit, @RequestParam(value = "month") String month
            , @RequestParam(value = "type") String type) {
        Date curMonth = DateUtil.getDateFromStr(month + "-01");
        Date start = DateUtil.getFirstDayMonth(curMonth);
        Date end = DateUtil.getEndDayMonth(curMonth);
        User user = new User();
        user.setPage(new Page<>(page, limit));
        return Response.OK(courseService.checkCompare(user, start, end, type));
    }



}
