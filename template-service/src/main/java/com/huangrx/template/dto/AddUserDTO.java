package com.huangrx.template.dto;

import com.huangrx.template.constant.Constants;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * <p>User DTO (传输对象)</p>
 *
 * @author huangrx
 * @since 2023/12/8 15:57
 */
@Data
public class AddUserDTO {

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "用户昵称")
    @NotBlank(message = "用户昵称不能为空")
    private String nickname;

    @ApiModelProperty(value = "邮件")
    @NotBlank(message = "邮件不能为空")
    @Pattern(regexp = Constants.Regex.EMAIL, message = "邮件格式不正确")
    private String email;

    @ApiModelProperty(value = "号码")
    @NotBlank(message = "号码不能为空")
    @Pattern(regexp = Constants.Regex.MOBILE, message = "手机号码格式不正确")
    private String phoneNumber;

    @ApiModelProperty(value = "性别")
    @NotNull(message = "性别不能为空")
    private Integer sex;

    @ApiModelProperty(value = "用户头像")
    @NotBlank(message = "用户头像不能为空")
    private String avatar;

    @ApiModelProperty(value = "用户密码")
    @NotBlank(message = "用户密码不能为空")
    @Pattern(regexp = Constants.Regex.PASSWORD_EIGHT, message = "密码格式不正确")
    private String password;

    @ApiModelProperty(value = "职位ID")
    @NotNull(message = "职位ID不能为空")
    private Long postId;

    @ApiModelProperty(value = "角色ID")
    @NotNull(message = "角色ID不能为空")
    private Long roleId;

    @ApiModelProperty(value = "部门ID")
    @NotNull(message = "部门ID不能为空")
    private Long deptId;

    @ApiModelProperty(value = "状态")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

}
