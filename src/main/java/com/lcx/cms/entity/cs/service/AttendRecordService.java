package com.lcx.cms.entity.cs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.entity.cs.entity.AttendRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lcx.cms.entity.cs.entity.Course;

import java.util.Date;
import java.util.List;

/**
 * 上课记录表（课时记录） service
 *
 * @author lcx
 * @since 2021-01-25
 */
public interface AttendRecordService extends IService<AttendRecord> {


    List<AttendRecord> findByTime(Integer currentUserId, Date startDate, Date endDate);

    List<AttendRecord> listMonths(Date month);

    Page<AttendRecord> pageAll(AttendRecord build);
}
