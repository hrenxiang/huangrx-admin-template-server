package com.huangrx.template.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p> 用户VO （响应对象）</p>
 *
 * @author huangrx
 * @since 2023/4/26 14:26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "职位ID")
    private Long postId;

    @ApiModelProperty(value = "职位名称")
    private String postName;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户类型")
    private Integer userType;

    @ApiModelProperty(value = "邮件")
    private String email;

    @ApiModelProperty(value = "号码")
    private String phoneNumber;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "IP")
    private String loginIp;

    @ApiModelProperty(value = "登录时间")
    private LocalDateTime loginDate;

    @ApiModelProperty(value = "创建者ID")
    private Long creatorId;

    @ApiModelProperty(value = "创建者")
    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改者ID")
    private Long updaterId;

    @ApiModelProperty(value = "修改者")
    private String updaterName;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;
}
