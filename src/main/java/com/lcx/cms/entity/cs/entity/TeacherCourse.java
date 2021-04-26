package com.lcx.cms.entity.cs.entity;

import static lombok.AccessLevel.PRIVATE;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

/**
 * 老师课程关联表
 *
 * @author lcx
 * @since 2021-01-25
 */
@Data
@FieldDefaults(level = PRIVATE)
@EqualsAndHashCode(callSuper = false)
@TableName("cms_teacher_course")
public class TeacherCourse {

    @TableId(value="id", type= IdType.AUTO)
    Integer id;

    /**
     * 课程id
     */
    Integer courseId;

    /**
     * 老师id
     */
    Integer userId;

    /**
     * 原来被分配的老师id
     */
    Integer originalUserId;

    Date createTime;

    /**
     * 状态
     */
    Integer status;

}
