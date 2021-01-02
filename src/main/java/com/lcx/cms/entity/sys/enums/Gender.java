package com.lcx.cms.entity.sys.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum Gender implements IEnum<Integer> {

    MAN(0),
    WOMAN(1),
    ;

    private int value;

    Gender(int value) {
        this.value = value;
    }
    @Override
    public Integer getValue() {
        return value;
    }
}
