package com.lcx.cms.entity.cs.entity;

import static lombok.AccessLevel.PRIVATE;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

/**
 * 老师班级关联表
 *
 * @author lcx
 * @since 2021-01-25
 */
@Data
@FieldDefaults(level = PRIVATE)
@EqualsAndHashCode(callSuper = false)
@TableName("cms_teacher_class")
public class TeacherClass {

    @TableId(value="id", type= IdType.AUTO)
    Integer id;

    /**
     * 老师id
     */
    Integer userId;

    /**
     * 班级id
     */
    Integer classId;

}
