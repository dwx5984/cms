package com.lcx.cms.entity.cs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.base.DateUtil;
import com.lcx.cms.entity.sys.entity.User;
import com.lcx.cms.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lcx.cms.base.Response;
import org.springframework.web.bind.annotation.RequestBody;
import com.lcx.cms.enums.Bool;


import com.lcx.cms.entity.cs.service.AttendWeekService;
import com.lcx.cms.entity.cs.entity.AttendWeek;

import java.util.Date;

/**
 * 周课时统计 controller
 *
 * @author lcx
 * @since 2021-01-25
 */
@RestController
@RequestMapping("/rest/attendWeeks")
public class AttendWeekController {

    @Autowired
    private AttendWeekService attendWeekService;

    @Autowired
    public AttendWeekController(AttendWeekService attendWeekService) {
        this.attendWeekService = attendWeekService;
    }


    /**
     * 查询AttendWeek
     *
     * @param id
     * @return AttendWeekDomain
     */
    @GetMapping("/{id}")
    public Response get(@PathVariable Integer id) {
        AttendWeek attendWeek = attendWeekService.getById(id);
        return Response.OK(attendWeek);
    }


    /**
     * 新增AttendWeek
     *
     * @return AttendWeekDomain
     */
    @PostMapping
    public Response save(@RequestParam("last") Boolean last) {
        attendWeekService.genWeek(RequestUtil.getCurrentUserId(), last);
        return Response.OK(true);
    }

    @PostMapping("genWeek")
    public Response genWeekByDate(@RequestParam("range") String range) {
        String[] split = range.split(" - ");
        Date start = DateUtil.getDateFromStr(split[0]);
        Date end = DateUtil.getDateFromStr(split[1]);
        attendWeekService.genWeekByDate(start, end);
        return Response.OK(true);
    }


    /**
     * 更新AttendWeek
     *
     * @param attendWeek
     * @return AttendWeekDomain
     */
    @PutMapping("/{id}")
    public Response update(@PathVariable Integer id, @RequestBody AttendWeek attendWeek) {
        boolean success = attendWeekService.updateById(attendWeek);
        return Response.OK(success ? Bool.Yes : Bool.No);
    }

    @GetMapping("/page")
    public Response page(Long page, Long limit) {
        AttendWeek week = new AttendWeek();
        week.setPage(new Page<>(page, limit));
        return Response.OK(attendWeekService.pageAll(week));
    }

    @GetMapping("/findUserWithHours")
    public Response findUserWithHours() {
        return Response.OK(attendWeekService.findUserWithHours());
    }

    @PostMapping("importWeek")
    public Response importWeek(@RequestParam("range") String range, @RequestParam("hour") Integer hour,
                               @RequestParam("userId") Integer userId) {
        String[] split = range.split(" - ");
        Date start = DateUtil.getDateFromStr(split[0]);
        Date end = DateUtil.getDateFromStr(split[1]);
        attendWeekService.importWeek(start, end, hour, userId);
        return Response.OK(true);
    }


}
