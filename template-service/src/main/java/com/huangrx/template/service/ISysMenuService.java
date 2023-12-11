package com.huangrx.template.service;

import com.huangrx.template.po.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author huangrx
 * @since 2023-11-26
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 获取指定角色ID的菜单列表
     *
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<SysMenu> getMenuListByRoleId(Long roleId);
}
