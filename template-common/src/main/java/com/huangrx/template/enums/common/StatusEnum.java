package com.huangrx.template.enums.common;

import com.huangrx.template.enums.BasicEnum;


/**
 *开关状态枚举
 *
 * @author   huangrx
 * @since   2023-12-16 18:32
 */
public enum StatusEnum implements BasicEnum<Integer> {
    /**
     * 开关状态
     */
    ENABLE(1, "正常"),
    DISABLE(0, "停用");

    private final int value;
    private final String description;

    StatusEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }


    @Override
    public Integer value() {
        return value;
    }

    @Override
    public String description() {
        return description;
    }
}