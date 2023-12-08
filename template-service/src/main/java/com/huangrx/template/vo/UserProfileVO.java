package com.huangrx.template.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>contains user vo and role name and post name （响应对象）</p>
 *
 * @author huangrx
 * @since 2023/12/8 15:53
 */
@Data
public class UserProfileVO {

    @ApiModelProperty(value = "UserVO")
    private UserVO user;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "岗位名称")
    private String postName;

}
