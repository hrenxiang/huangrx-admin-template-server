package com.huangrx.template.user.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 登录请求
 *
 * @author   huangrx
 * @since   2023-07-26 19:02
 */
@Data
public class LoginDTO implements Serializable {

    @Serial
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
