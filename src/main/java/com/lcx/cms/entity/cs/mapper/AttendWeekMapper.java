package com.lcx.cms.entity.cs.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.entity.cs.entity.AttendWeek;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* 周课时统计 Mapper 接口
*
* @author lcx
* @since 2021-01-25
*/
public interface AttendWeekMapper extends BaseMapper<AttendWeek> {

    List<AttendWeek> pageAll(Page<AttendWeek> page, AttendWeek week);
}