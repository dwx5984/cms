package com.lcx.cms.entity.cs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lcx.cms.base.Response;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import com.lcx.cms.base.Response;
import com.lcx.cms.enums.Bool;


import com.lcx.cms.entity.cs.service.TeacherClassService;
import com.lcx.cms.entity.cs.entity.TeacherClass;

/**
 * 老师班级关联表 controller
 *
 * @author lcx
 * @since 2021-01-25
 */
@RestController
@RequestMapping("/rest/teacherClasss")
public class TeacherClassController {

    @Autowired
    private TeacherClassService teacherClassService;

    @Autowired
    public TeacherClassController(TeacherClassService teacherClassService){
        this.teacherClassService= teacherClassService;
    }


/**
 * 查询TeacherClass
 *
 * @param id
 *
 * @return TeacherClassDomain
 */
@GetMapping("/{id}")
public Response get(@PathVariable Integer id){
    TeacherClass teacherClass = teacherClassService.getById(id);
        return Response.OK(teacherClass);
        }


/**
 * 新增TeacherClass
 *
 * @param teacherClass
 *
 * @return TeacherClassDomain
 */
@PostMapping
public Response save(@RequestBody TeacherClass teacherClass){
    teacherClassService.save(teacherClass);
        return Response.OK(teacherClass);
    }

/**
 * 更新TeacherClass
 *
 * @param teacherClass
 *
 * @return TeacherClassDomain
 */
@PutMapping("/{id}")
public Response update(@PathVariable Integer id,@RequestBody TeacherClass teacherClass) {
    boolean success = teacherClassService.updateById(teacherClass);
    return Response.OK(success ? Bool.Yes : Bool.No);
    }

        }
