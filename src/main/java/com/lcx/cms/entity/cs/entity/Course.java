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
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

/**
 * 
 *
 * @author lcx
 * @since 2021-01-25
 */
@Data
@FieldDefaults(level = PRIVATE)
@EqualsAndHashCode(callSuper = false)
@TableName("cms_course")
public class Course {

    @TableId(value="id", type= IdType.AUTO)
    Integer id;

    /**
     * 课程代码
     */
    String no;

    String name;

    /**
     * 学分
     */
    Double credit;

    /**
     * 课时
     */
    Double period;

    /**
     * 学科
     */
    String subject;

    Date createTime;

    /**
     * 删除标记
     */
    Integer delFlag;

    @TableField(exist = false)
    Page<Course> page;

    @TableField(exist = false)
    Integer attendedHours;

}
