package com.lcx.cms.entity.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcx.cms.entity.sys.enums.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static lombok.AccessLevel.PRIVATE;

/**
 * @author lcx
 * @since 2020-12-07
 */
@Data
@FieldDefaults(level = PRIVATE)
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
public class User {

    Long id;

    /**
     * 性别
     */
    @NotNull(message = "性别不能为空")
    Gender gender;

    /**
     * 用户名称
     */
    @NotBlank(message = "姓名不能为空")
    String name;

    /**
     * 工号
     */
    @NotBlank(message = "工号不能为空")
    String number;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)// 不返回给前端
    String password;

    /**
     * 手机号码
     */
    @NotBlank(message = "手机号不能为空")
    String mobile;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    String email;

    /**
     * 国家
     */
    String country;

    /**
     * 省
     */
    String province;

    /**
     * 市
     */
    String city;

    /**
     * 区
     */
    String district;

    /**
     * 详细地址
     */
    String address;

    /**
     * 状态
     */
    Integer status;

    Date createTime;

    /**
     * 登录名
     */
    @TableField(exist = false)
    String loginName;

    /**
     * 分页
     */
    @TableField(exist = false)
    Page<User> page;

    /**
     * 角色
     */
    @TableField(exist = false)
    Role role;
}
