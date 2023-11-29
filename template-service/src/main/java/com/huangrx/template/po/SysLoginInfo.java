package com.huangrx.template.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.huangrx.template.core.base.BaseEntity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 系统访问记录
 * </p>
 *
 * @author huangrx
 * @since 2023-11-26
 */
@Getter
@Setter
@TableName("t_sys_login_info")
@ApiModel(value = "SysLoginInfo对象", description = "系统访问记录")
@EqualsAndHashCode(callSuper = true)
public class SysLoginInfo extends BaseEntity<SysLoginInfo> {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("访问ID")
    @TableId(value = "info_id", type = IdType.AUTO)
    private Long infoId;

    @ApiModelProperty("用户账号")
    @TableField("username")
    private String username;

    @ApiModelProperty("登录IP地址")
    @TableField("ip_address")
    private String ipAddress;

    @ApiModelProperty("登录地点")
    @TableField("login_location")
    private String loginLocation;

    @ApiModelProperty("浏览器类型")
    @TableField("browser")
    private String browser;

    @ApiModelProperty("操作系统")
    @TableField("operation_system")
    private String operationSystem;

    @ApiModelProperty("登录状态（1成功 0失败）")
    @TableField("`status`")
    private Integer status;

    @ApiModelProperty("提示消息")
    @TableField("msg")
    private String msg;

    @ApiModelProperty("访问时间")
    @TableField("login_time")
    private LocalDateTime loginTime;

    @Override
    public Serializable pkVal() {
        return this.infoId;
    }
}
