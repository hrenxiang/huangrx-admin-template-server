package com.huangrx.template.user.enums;

import com.huangrx.template.enums.BasicEnum;


/**
 * 登录方式枚举
 *
 * @author   huangrx
 * @since   2023-04-26 02:09
 */
public enum LoginType implements BasicEnum<String> {

    /**
     * 登录类型
     */
    NORMAL("normal", "用户名密码登录(默认)"),

    REFRESH_TOKEN("refresh_token", "刷新令牌登录"),

    WECHAT("wechat", "微信登录")

    ;

    private final String value;
    private final String description;

    LoginType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String value() {
        return this.value;
    }

    @Override
    public String description() {
        return this.description;
    }
}
