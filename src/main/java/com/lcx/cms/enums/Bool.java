package com.lcx.cms.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 是否
 * 枚举类
 */
public enum Bool implements IEnum<Integer> {
    Yes(0, "是"),
    No(1, "否"),
    ;

    private int value;

    private String label;

    Bool(int value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
