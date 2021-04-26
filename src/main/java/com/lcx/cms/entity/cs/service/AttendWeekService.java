package com.lcx.cms.entity.cs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.entity.cs.entity.AttendWeek;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lcx.cms.entity.sys.entity.User;

import java.util.Date;
import java.util.List;

/**
 * 周课时统计 service
 *
 * @author lcx
 * @since 2021-01-25
 */
public interface AttendWeekService extends IService<AttendWeek> {


    Page<AttendWeek> pageAll(AttendWeek week);

    void genWeek(Integer currentUserId, boolean isLastWeek);

    void genWeekByDate(Date start, Date end);

    List<User> findUserWithHours();

    void importWeek(Date start, Date end, Integer hour, Integer userId);
}
