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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

/**
 * 上课记录表（课时记录）
 *
 * @author lcx
 * @since 2021-01-25
 */
@Data
@FieldDefaults(level = PRIVATE)
@EqualsAndHashCode(callSuper = false)
@TableName("cms_attend_record")
public class AttendRecord {

    @TableId(value = "id", type = IdType.AUTO)
    Integer id;

    /**
     * 老师id
     */
    Integer userId;

    /**
     * 课程id
     */
    Integer courseId;

    /**
     * 记录状态
     */
    Integer status;

    Integer hour;

    /**
     * 上课开始时间
     */
    Date attendStartTime;

    /**
     * 上课结束时间
     */
    Date attendEndTime;

    Date createTime;

    @TableField(exist = false)
    Course  course;

    @TableField(exist = false)
    Integer totalHours;

    @TableField(exist = false)
    Page<AttendRecord> page;

}
