package com.lcx.cms.entity.cs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.entity.cs.enums.ExchangeStatus;
import com.lcx.cms.entity.cs.enums.ExchangeType;
import com.lcx.cms.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lcx.cms.base.Response;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import com.lcx.cms.base.Response;
import com.lcx.cms.enums.Bool;


import com.lcx.cms.entity.cs.service.ExchangeRecordService;
import com.lcx.cms.entity.cs.entity.ExchangeRecord;

/**
 * 调课记录 controller
 *
 * @author lcx
 * @since 2021-01-25
 */
@RestController
@RequestMapping("/rest/exchangeRecords")
public class ExchangeRecordController {

    @Autowired
    private ExchangeRecordService exchangeRecordService;

    @Autowired
    public ExchangeRecordController(ExchangeRecordService exchangeRecordService) {
        this.exchangeRecordService = exchangeRecordService;
    }


    /**
     * 查询ExchangeRecord
     *
     * @param id
     * @return ExchangeRecordDomain
     */
    @GetMapping("/{id}")
    public Response get(@PathVariable Integer id) {
        ExchangeRecord exchangeRecord = exchangeRecordService.getById(id);
        return Response.OK(exchangeRecord);
    }

    @GetMapping("/page")
    public Response page(Long page, Long limit, @RequestParam(required = false) ExchangeStatus status) {
        ExchangeRecord exchangeRecord = new ExchangeRecord();
        exchangeRecord.setStatus(status);
        exchangeRecord.setPage(new Page<>(page, limit));
        Page<ExchangeRecord> pageAll = exchangeRecordService.pageAll(exchangeRecord);
        return Response.OK(pageAll);
    }


    /**
     * 新增ExchangeRecord
     *
     * @param exchangeRecord
     * @return ExchangeRecordDomain
     */
    @PostMapping
    public Response save(@RequestBody ExchangeRecord exchangeRecord) {
        Integer currentUserId = RequestUtil.getCurrentUserId();
        exchangeRecord.setUserId(currentUserId);
        exchangeRecord.setStatus(ExchangeStatus.PENDING);
        exchangeRecordService.save(exchangeRecord);
        return Response.OK(exchangeRecord);
    }

    /**
     * 更新ExchangeRecord
     *
     * @param exchangeRecord
     * @return ExchangeRecordDomain
     */
    @PutMapping("/{id}")
    public Response update(@PathVariable Integer id, @RequestBody ExchangeRecord exchangeRecord) {
        boolean success = exchangeRecordService.updateById(exchangeRecord);
        return Response.OK(success ? Bool.Yes : Bool.No);
    }

    @PutMapping("/audit/{id}")
    public Response update(@PathVariable Integer id, ExchangeStatus status) {
        ExchangeRecord exchangeRecord = new ExchangeRecord();
        exchangeRecord.setId(id);
        exchangeRecord.setStatus(status);
        exchangeRecordService.audit(exchangeRecord);
        return Response.OK(Bool.Yes);
    }

}
