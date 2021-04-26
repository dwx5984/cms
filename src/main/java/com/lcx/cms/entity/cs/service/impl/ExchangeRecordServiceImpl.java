package com.lcx.cms.entity.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.entity.cs.entity.ExchangeRecord;
import com.lcx.cms.entity.cs.entity.TeacherCourse;
import com.lcx.cms.entity.cs.enums.ExchangeStatus;
import com.lcx.cms.entity.cs.mapper.ExchangeRecordMapper;
import com.lcx.cms.entity.cs.service.ExchangeRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcx.cms.entity.cs.service.TeacherCourseService;
import com.lcx.cms.enums.Bool;
import com.lcx.cms.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 调课记录 service实现
 *
 * @author lcx
 * @since 2021-01-25
 */
@Slf4j
@Service
public class ExchangeRecordServiceImpl extends ServiceImpl<ExchangeRecordMapper, ExchangeRecord> implements ExchangeRecordService {

    @Autowired
    TeacherCourseService teacherCourseService;


    @Override
    public void audit(ExchangeRecord exchangeRecord) {
        ExchangeRecord byId = getById(exchangeRecord.getId());
        if (exchangeRecord.getStatus() == ExchangeStatus.PASS) {
            TeacherCourse teacherCourse = new TeacherCourse();
            teacherCourse.setUserId(byId.getTargetUserId());
            teacherCourse.setCourseId(byId.getCourseId());
            TeacherCourse one = teacherCourseService.getOne(new QueryWrapper<>(teacherCourse));
            if (one == null) {
                teacherCourse.setStatus(Bool.Yes.getValue());
                teacherCourse.setOriginalUserId(byId.getUserId());
                teacherCourseService.save(teacherCourse);
            }
        }
        updateById(exchangeRecord);
    }

    @Override
    public Page<ExchangeRecord> pageAll(ExchangeRecord exchangeRecord) {
        Page<ExchangeRecord> page = exchangeRecord.getPage();
        page.setRecords(baseMapper.pageAll(page, exchangeRecord));
        return page;
    }
}