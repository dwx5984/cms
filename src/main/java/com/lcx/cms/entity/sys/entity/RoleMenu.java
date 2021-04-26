package com.lcx.cms.entity.sys.entity;

import static lombok.AccessLevel.PRIVATE;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

/**
 * 
 *
 * @author lcx
 * @since 2020-12-10
 */
@Data
@FieldDefaults(level = PRIVATE)
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_menu")
public class RoleMenu {

    @TableId(value="id", type= IdType.AUTO)
    Integer id;

    /**
     * 角色id
     */
    Integer roleId;

    Integer menuId;

}
