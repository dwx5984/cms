package com.lcx.cms.entity.cs.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum ExchangeStatus implements IEnum<Integer> {
    /**
     * 待审核
     */
    PENDING(1),

    /**
     * 通过
     */
    PASS(2),

    /**
     * 拒绝
     */
    REJECT(3);


    private final int value;
    ExchangeStatus(int value) {
        this.value = value;
    }
    @Override
    public Integer getValue() {
        return value;
    }
}
