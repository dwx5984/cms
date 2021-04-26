package com.lcx.cms.entity.cs.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.base.DateUtil;
import com.lcx.cms.entity.cs.entity.AttendRecord;
import com.lcx.cms.entity.cs.entity.Course;
import com.lcx.cms.entity.cs.mapper.AttendRecordMapper;
import com.lcx.cms.entity.cs.service.AttendRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcx.cms.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 上课记录表（课时记录） service实现
 *
 * @author lcx
 * @since 2021-01-25
 */
@Slf4j
@Service
public class AttendRecordServiceImpl extends ServiceImpl<AttendRecordMapper, AttendRecord> implements AttendRecordService {

    @Override
    public List<AttendRecord> findByTime(Integer currentUserId, Date startDate, Date endDate) {
        return baseMapper.findByTime(currentUserId, startDate, endDate);
    }

    @Override
    public List<AttendRecord> listMonths(Date month) {
        Date firstDayMonth = DateUtil.getFirstDayMonth(month);
        Date endDayMonth = DateUtil.getEndDayMonth(month);
        Integer currentUserId = RequestUtil.getCurrentUserId();
        return baseMapper.findMonth(firstDayMonth, endDayMonth, currentUserId);
    }

    @Override
    public Page<AttendRecord> pageAll(AttendRecord build) {
        Page<AttendRecord> page = build.getPage();
        List<AttendRecord> list = baseMapper.pageAll(page, build);
        int sum = list.stream().mapToInt(AttendRecord::getHour).sum();
        list.forEach(i -> i.setTotalHours(sum));
        page.setRecords(list);
        return page;
    }
}