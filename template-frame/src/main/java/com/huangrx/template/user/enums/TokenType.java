package com.huangrx.template.user.enums;

import com.huangrx.template.enums.BasicEnum;
import lombok.Getter;

/**
 * @author huangrx
 * @since 2023/4/25 21:35
 */
@Getter
public enum TokenType implements BasicEnum<Integer> {

    /**
     * Token类型 用户名密码登录，手机号验证码登录等
     */
    NORMAL(0, "用户名密码登录"),

    REFRESH_TOKEN(1, "刷新令牌登录"),

    WECHAT(2, "微信登录");

    private final Integer value;

    private final String description;

    TokenType(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public Integer value() {
        return this.value;
    }

    @Override
    public String description() {
        return this.description;
    }
}
