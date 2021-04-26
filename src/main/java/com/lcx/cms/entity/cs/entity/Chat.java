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
import com.lcx.cms.entity.sys.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

/**
 * 
 *
 * @author lcx
 * @since 2021-02-27
 */
@Data
@FieldDefaults(level = PRIVATE)
@EqualsAndHashCode(callSuper = false)
@TableName("cms_chat")
public class Chat {

    @TableId(value="id", type= IdType.AUTO)
    Integer id;

    Integer senderId;

    Integer teacherId;

    String content;

    Integer readed;

    Date createTime;

    @TableField(exist = false)
    User teacher;

    @TableField(exist = false)
    User sender;

    @TableField(exist = false)
    Page<Chat> page;

    @TableField(exist = false)
    String senderRole;

    @TableField(exist = false)
    String status;


}
