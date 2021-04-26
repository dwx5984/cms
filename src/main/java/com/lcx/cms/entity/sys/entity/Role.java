package com.lcx.cms.entity.sys.entity;

import static lombok.AccessLevel.PRIVATE;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.entity.sys.enums.RoleType;
import com.lcx.cms.enums.Bool;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

/**
 * 用户角色表
 *
 * @author lcx
 * @since 2020-12-07
 */
@Data
@FieldDefaults(level = PRIVATE)
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role")
public class Role {

    @TableId(value="id", type= IdType.AUTO)
    Integer id;

    String name;

    Bool status;

    /**
     * 角色类型
     */
    RoleType type;

    /**
     * 角色菜单关联
     */
    @TableField(exist = false)
    List<RoleMenu> roleMenus;

    /**
     * 角色对应的菜单
     */
    @TableField(exist = false)
    List<Menu> menus;


    @TableField(exist = false)
    Page<Role> page;

}
