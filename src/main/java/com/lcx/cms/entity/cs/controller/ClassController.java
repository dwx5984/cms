package com.lcx.cms.entity.cs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lcx.cms.base.Response;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import com.lcx.cms.base.Response;
import com.lcx.cms.enums.Bool;


import com.lcx.cms.entity.cs.service.ClassService;
import com.lcx.cms.entity.cs.entity.Class;

/**
 * 班级表 controller
 *
 * @author lcx
 * @since 2021-01-25
 */
@RestController
@RequestMapping("/rest/classs")
public class ClassController {

    @Autowired
    private ClassService classService;

    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }


    /**
     * 查询Class
     *
     * @param id
     * @return ClassDomain
     */
    @GetMapping("/{id}")
    public Response get(@PathVariable Integer id) {
        Class clazz = classService.getById(id);
        return Response.OK(clazz);
    }


    /**
     * 新增Class
     *
     * @param clazz
     * @return ClassDomain
     */
    @PostMapping
    public Response save(@RequestBody Class clazz) {
        classService.save(clazz);
        return Response.OK(clazz);
    }

    /**
     * 更新Class
     *
     * @param clazz
     * @return ClassDomain
     */
    @PutMapping("/{id}")
    public Response update(@PathVariable Integer id, @RequestBody Class clazz) {
        boolean success = classService.updateById(clazz);
        return Response.OK(success ? Bool.Yes : Bool.No);
    }

}
