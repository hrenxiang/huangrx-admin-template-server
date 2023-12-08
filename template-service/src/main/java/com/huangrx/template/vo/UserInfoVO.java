package com.huangrx.template.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>contains user vo and role vo （响应对象）</p>
 *
 * @author huangrx
 * @since 2023/12/8 15:53
 */
@Data
public class UserInfoVO {

    @ApiModelProperty(value = "UserVO")
    private UserVO user;

    @ApiModelProperty(value = "RoleVO")
    private RoleVO role;

}
