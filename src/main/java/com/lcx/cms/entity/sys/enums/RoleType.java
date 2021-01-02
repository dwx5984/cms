package com.lcx.cms.entity.sys.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum RoleType implements IEnum<Integer> {


    /**
     * 管理员
     */
    ADMIN(0),
    /**
     * 普通用户
     */
    NORMAL(1),
    ;

    private final int value;

    RoleType(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}