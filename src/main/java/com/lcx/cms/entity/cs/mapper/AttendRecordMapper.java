package com.lcx.cms.entity.cs.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.entity.cs.entity.AttendRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* 上课记录表（课时记录） Mapper 接口
*
* @author lcx
* @since 2021-01-25
*/
public interface AttendRecordMapper extends BaseMapper<AttendRecord> {

    List<AttendRecord> findByTime(@Param("userId") Integer userId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<AttendRecord> findMonth(@Param("firstDayMonth") Date firstDayMonth, @Param("endDayMonth") Date endDayMonth, @Param("userId") Integer userId);

    List<AttendRecord> pageAll(Page<AttendRecord> page, AttendRecord build);
}