package com.huangrx.template.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.huangrx.template.core.base.BaseEntity;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author huangrx
 * @since 2023-11-26
 */
@Getter
@Setter
@TableName("t_sys_menu")
@ApiModel(value = "SysMenu对象", description = "菜单权限表")
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends BaseEntity<SysMenu> {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("菜单ID")
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long menuId;

    @ApiModelProperty("菜单名称")
    @TableField("menu_name")
    private String menuName;

    @ApiModelProperty("菜单的类型(1为普通菜单2为目录3为内嵌iFrame4为外链跳转)")
    @TableField("menu_type")
    private Integer menuType;

    @ApiModelProperty("路由名称（需保持和前端对应的vue文件中的name保持一致defineOptions方法中设置的name）")
    @TableField("router_name")
    private String routerName;

    @ApiModelProperty("父菜单ID")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty("组件路径（对应前端项目view文件夹中的路径）")
    @TableField("`path`")
    private String path;

    @ApiModelProperty("是否按钮")
    @TableField("is_button")
    private Boolean isButton;

    @ApiModelProperty("权限标识")
    @TableField("permission")
    private String permission;

    @ApiModelProperty("路由元信息（前端根据这个信息进行逻辑处理）")
    @TableField("meta_info")
    private String metaInfo;

    @ApiModelProperty("菜单状态（1启用 0停用）")
    @TableField("`status`")
    private Integer status;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;

    @Override
    public Serializable pkVal() {
        return this.menuId;
    }
}
