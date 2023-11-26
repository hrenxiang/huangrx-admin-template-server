package com.huangrx.template.security.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * 登录请求VO
 *
 * @author   huangrx
 * @since   2023-07-26 19:02
 */
@Setter
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoginRequest implements Serializable {
    private static final long serialVersionUID = -7163038793835108044L;
    private String username;
    private String password;
    private String mobile;
    private String loginType;
    /**
     * 微信：openId
     */
    private String openId;
    /**
     * 微信：access_token
     */
    private String wxToken;
}
