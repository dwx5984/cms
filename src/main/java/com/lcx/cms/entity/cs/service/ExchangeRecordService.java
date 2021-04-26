package com.lcx.cms.entity.cs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.entity.cs.entity.ExchangeRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 调课记录 service
 *
 * @author lcx
 * @since 2021-01-25
 */
public interface ExchangeRecordService extends IService<ExchangeRecord> {


    void audit(ExchangeRecord exchangeRecord);

    Page<ExchangeRecord> pageAll(ExchangeRecord exchangeRecord);
}
