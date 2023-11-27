package com.huangrx.template.user.token;

import lombok.Getter;

/**
 * @author huangrx
 * @since 2023/4/25 21:35
 */
@Getter
public enum TokenType {

    /**
     * Token类型 用户名密码登录，手机号验证码登录等
     */
    NORMAL(0, "用户名密码登录"),
    WECHAT(1, "微信登录");

    private final Integer code;
    private final String desc;

    TokenType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
