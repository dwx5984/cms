package com.lcx.cms.entity.sys.entity;

import static lombok.AccessLevel.PRIVATE;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

/**
 * 用户角色关联表
 *
 * @author lcx
 * @since 2020-12-10
 */
@Data
@FieldDefaults(level = PRIVATE)
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_user")
public class RoleUser {

    @TableId(value="id", type= IdType.AUTO)
    Integer id;

    /**
     * 用户id
     */
    Integer userId;

    /**
     * 角色id
     */
    Integer roleId;

    /**
     * 创建时间
     */
    Date createTime;

    /**
     * 创建人
     */
    Integer createBy;

}
