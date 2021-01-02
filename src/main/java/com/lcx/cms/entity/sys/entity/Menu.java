package com.lcx.cms.entity.sys.entity;

import static lombok.AccessLevel.PRIVATE;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.enums.Bool;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

/**
 * @author lcx
 * @since 2020-12-07
 */
@Data
@FieldDefaults(level = PRIVATE)
@EqualsAndHashCode(callSuper = false)
@TableName("sys_menu")
public class Menu {

    Long id;

    /**
     * 菜单名
     */
    String name;

    /**
     * 状态
     */
    Bool status;

    /**
     * 排序
     */
    Integer sort;


    /**
     * 路径
     */
    String path;

    /**
     * 上级菜单
     */
    Long parentId;

    Long createBy;

    Date createTime;

    /**
     * 子菜单
     */
    @TableField(exist = false)
    List<Menu> child;

    /**
     * 分页
     */
    @TableField(exist = false)
    Page<Menu> page;

}
