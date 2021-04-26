package com.lcx.cms.entity.cs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.base.AppException;
import com.lcx.cms.base.DateUtil;
import com.lcx.cms.entity.cs.entity.Course;
import com.lcx.cms.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lcx.cms.base.Response;
import com.lcx.cms.enums.Bool;


import com.lcx.cms.entity.cs.service.AttendRecordService;
import com.lcx.cms.entity.cs.entity.AttendRecord;

import java.util.Date;
import java.util.List;

/**
 * 上课记录表（课时记录） controller
 *
 * @author lcx
 * @since 2021-01-25
 */
@RestController
@RequestMapping("/rest/attendRecords")
public class AttendRecordController {

    @Autowired
    private AttendRecordService attendRecordService;

    @Autowired
    public AttendRecordController(AttendRecordService attendRecordService) {
        this.attendRecordService = attendRecordService;
    }


    /**
     * 查询AttendRecord
     *
     * @param id
     * @return AttendRecordDomain
     */
    @GetMapping("/{id}")
    public Response get(@PathVariable Long id) {
        AttendRecord attendRecord = attendRecordService.getById(id);
        return Response.OK(attendRecord);
    }


    /**
     * 新增AttendRecord
     *
     * @param attendRecord
     * @return AttendRecordDomain
     */
    @PostMapping
    public Response save(@RequestBody AttendRecord attendRecord) {
        long hour = (attendRecord.getAttendEndTime().getTime() - attendRecord.getAttendStartTime().getTime()) / (1000 * 60) / 60;
        if (hour < 1 || hour > 2) {
            throw AppException.illegal("上课时间选择错误");
        }
        attendRecord.setStatus(Bool.Yes.getValue());
        attendRecord.setUserId(RequestUtil.getCurrentUserId());
        attendRecord.setHour((int) hour);
        attendRecordService.save(attendRecord);
        return Response.OK(attendRecord);
    }

    /**
     * 更新AttendRecord
     *
     * @param attendRecord
     * @return AttendRecordDomain
     */
    @PutMapping("/{id}")
    public Response update(@PathVariable Long id, @RequestBody AttendRecord attendRecord) {
        boolean success = attendRecordService.updateById(attendRecord);
        return Response.OK(success ? Bool.Yes : Bool.No);
    }



    @GetMapping("/listMonths")
    public Response listMonths(@RequestParam("month") String month) {
        Date dateFromStr = DateUtil.getDateFromStr(month);
        List<AttendRecord> attendRecords = attendRecordService.listMonths(dateFromStr);
        return Response.OK(attendRecords);
    }

    @GetMapping("pageByUser")
    public Response pageByUser(Long page, Long limit, @RequestParam(required = false) Integer userId
            , @RequestParam(required = false) Integer courseId) {
        AttendRecord build = new AttendRecord();
        build.setUserId(userId);
        build.setCourseId(courseId);
        build.setPage(new Page<>(page, limit));
        Page<AttendRecord> courses = attendRecordService.pageAll(build);
        return Response.OK(courses);
    }

}
