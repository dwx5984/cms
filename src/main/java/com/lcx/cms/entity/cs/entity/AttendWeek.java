package com.lcx.cms.entity.cs.entity;

import static lombok.AccessLevel.PRIVATE;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

/**
 * 周课时统计
 *
 * @author lcx
 * @since 2021-01-25
 */
@Data
@FieldDefaults(level = PRIVATE)
@EqualsAndHashCode(callSuper = false)
@TableName("cms_attend_week")
public class AttendWeek {

    @TableId(type = IdType.AUTO)
    Integer id;

    /**
     * 统计开始时间
     */
    Date startTime;

    /**
     * 课程id
     */
    String courseIds;

    /**
     * 统计结束时间
     */
    Date endTime;

    /**
     * 老师id
     */
    Integer userId;

    /**
     * 导入时间
     */
    Date createTime;

    Integer hours;

    Integer importRole;

    /**
     * 每年的第几周
     */
    @TableField(exist = false)
    Integer week;

    @TableField(exist = false)
    List<Course> courses;

    @TableField(exist = false)
    Page<AttendWeek> page;

}
