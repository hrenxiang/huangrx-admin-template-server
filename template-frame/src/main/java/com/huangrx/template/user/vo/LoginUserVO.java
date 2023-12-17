package com.huangrx.template.user.vo;

import lombok.Data;

import java.util.List;

/**
 * <p> 登录用户VO </p>
 *
 * @author huangrx
 * @since 2023/12/15 13:44
 */
@Data
public class LoginUserVO {

    private UserVO userInfo;
    private String roleKey;
    private List<String> permissions;

}
