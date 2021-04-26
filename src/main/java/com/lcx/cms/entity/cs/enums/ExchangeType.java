package com.lcx.cms.entity.cs.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum ExchangeType implements IEnum<Integer> {
    /**
     * 代课
     */
    REPLACE(1),

    /**
     *请假
     */
    LEAVE(2);


    private final int value;
    ExchangeType(int value) {
        this.value = value;
    }
    @Override
    public Integer getValue() {
        return value;
    }
}
