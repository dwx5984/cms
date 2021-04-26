package com.lcx.cms.entity.cs.entity;

import static lombok.AccessLevel.PRIVATE;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.entity.cs.enums.ExchangeStatus;
import com.lcx.cms.entity.cs.enums.ExchangeType;
import com.lcx.cms.entity.sys.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

/**
 * 调课记录
 *
 * @author lcx
 * @since 2021-01-25
 */
@Data
@FieldDefaults(level = PRIVATE)
@EqualsAndHashCode(callSuper = false)
@TableName("cms_exchange_record")
public class ExchangeRecord {

    @TableId(value="id", type= IdType.AUTO)
    Integer id;

    /**
     * 调课类型：请假、调课
     */
    ExchangeType type;

    /**
     * 状态
     */
    ExchangeStatus status;

    Date createTime;

    /**
     * 某个老师的课程
     */
    Integer userId;

    Date startTime;

    Date endTime;


    /**
     * 课程id
     */
    Integer courseId;

    /**
     * 调课给哪个老师
     */
    Integer targetUserId;

    @TableField(exist = false)
    Page<ExchangeRecord> page;

    @TableField(exist = false)
    User user;

    @TableField(exist = false)
    User targetUser;

    @TableField(exist = false)
    Course course;
}
