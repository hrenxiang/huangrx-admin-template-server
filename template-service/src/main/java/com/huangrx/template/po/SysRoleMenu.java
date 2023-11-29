package com.huangrx.template.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.huangrx.template.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 角色和菜单关联表
 * </p>
 *
 * @author huangrx
 * @since 2023-11-26
 */
@Getter
@Setter
@TableName("t_sys_role_menu")
@ApiModel(value = "SysRoleMenu对象", description = "角色和菜单关联表")
@EqualsAndHashCode(callSuper = true)
public class SysRoleMenu extends BaseEntity<SysRoleMenu> {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色ID")
    @TableField(value = "role_id")
    private Long roleId;

    @ApiModelProperty("菜单ID")
    @TableField(value = "menu_id")
    private Long menuId;

    @Override
    public Serializable pkVal() {
        return this.menuId;
    }
}
