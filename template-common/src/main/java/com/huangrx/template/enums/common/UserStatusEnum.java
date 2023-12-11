package com.huangrx.template.enums.common;

import com.huangrx.template.enums.BasicEnum;

/**
 * <p> 用户账号状态枚举 </p>
 *
 * @author huangrx
 * @since 2023/12/11 20:19
 */
public enum UserStatusEnum implements BasicEnum<Integer> {

    /**
     * 正常
     */
    NORMAL(1, "正常"),
    /**
     * 禁用
     */
    DISABLED(2, "禁用"),
    /**
     * 冻结
     */
    FROZEN(3, "冻结");

    private final Integer value;

    private final String description;

    UserStatusEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public Integer value() {
        return value;
    }

    @Override
    public String description() {
        return this.description;
    }
}
