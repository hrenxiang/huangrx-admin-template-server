package com.huangrx.template.enums.common;

import com.huangrx.template.enums.BasicEnum;

/**
 * 用户状态
 *
 * @author valarchie
 */
public enum LoginStatusEnum implements BasicEnum<Integer> {

    /**
     * 登录成功
     */
    LOGIN_SUCCESS(1, "登录成功"),

    /**
     * 退出成功
     */
    LOGOUT(2, "退出成功"),

    /**
     * 注册
     */
    REGISTER(3, "注册"),

    /**
     * 登录失败
     */
    LOGIN_FAIL(0, "登录失败");

    private final Integer value;

    private final String description;

    LoginStatusEnum(Integer status, String description) {
        this.value = status;
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
