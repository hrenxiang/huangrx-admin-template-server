package com.huangrx.template.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 路由显示信息
 * 必须加上@JsonInclude(Include.NON_NULL)的注解  否则传null值给Vue动态路由渲染时会出错
 *
 * @author   huangrx
 * @since   2023-12-16 18:28
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetaDTO {
    /**
     * 菜单名称（兼容国际化、非国际化，如果用国际化的写法就必须在根目录的locales文件夹下对应添加）
     */
    private String title;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 是否显示该菜单
     */
    private Boolean showLink;
    /**
     * 是否显示父级菜单
     */
    private Boolean showParent;
    /**
     * 页面级别权限设置
     */
    private List<String> roles;
    /**
     * 按钮级别权限设置
     */
    private List<String> auths;
    /**
     * 需要内嵌的iframe链接地址
     */
    private String frameSrc;
    /**
     * 是否是内部页面   使用frameSrc来嵌入页面时，当isFrameSrcInternal=true的时候, 前端需要做特殊处理
     * 比如链接是 /druid/login.html
     * 前端需要处理成 <a href="http://localhost:8080/druid/login.html">...</a>
     */
    private Boolean isFrameSrcInternal;
    /**
     * 菜单排序，值越高排的越后（只针对顶级路由）
     */
    private Integer rank;
}