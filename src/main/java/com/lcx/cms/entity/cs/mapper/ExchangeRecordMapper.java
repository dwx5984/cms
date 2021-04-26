package com.lcx.cms.entity.cs.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.entity.cs.entity.ExchangeRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* 调课记录 Mapper 接口
*
* @author lcx
* @since 2021-01-25
*/
public interface ExchangeRecordMapper extends BaseMapper<ExchangeRecord> {

    List<ExchangeRecord> pageAll(Page<ExchangeRecord> page, ExchangeRecord exchangeRecord);
}