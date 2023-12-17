package com.huangrx.template.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import com.huangrx.template.utils.jackson.JacksonUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 动态路由信息
 * 必须加上@JsonInclude(Include.NON_NULL)的注解  否则传null值给Vue动态路由渲染时会出错
 *
 * @author   huangrx
 * @since   2023-12-16 18:23
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouterDTO {

    /**
     * 路由名字  根据前端的要求   必须唯一
     * 并按照前端项目的推荐  这个Name最好和组件的Name一样  使用菜单表中的 router_name
     * 这里后端需要加校验
     */
    private String name;

    /**
     * 路由路径地址
     */
    private String path;

    /**
     * 路由重定向
     */
    private String redirect;

    /**
     * 组件地址
     */
    private String component;

    /**
     * 一级菜单排序值（排序仅支持一级菜单）
     */
    private Integer rank;


    /**
     * 其他元素
     */
    private MetaDTO meta;

    /**
     * 子路由
     */
    private List<RouterDTO> children;

}