package com.huangrx.template.user.web;

import lombok.Getter;


/**
 * 登录方式枚举
 *
 * @author   huangrx
 * @since   2023-04-26 02:09
 */
@Getter
public enum LoginType {

    /**
     * 登录类型
     */
    NORMAL("normal", "用户名密码登录(默认)"),

    REFRESH_TOKEN("refresh_token", "刷新令牌登录"),

    WECHAT("wechat", "微信登录")

    ;

    private final String code;
    private final String desc;

    LoginType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
