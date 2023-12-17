package com.huangrx.template.service;

import com.huangrx.template.dto.RouterDTO;
import com.huangrx.template.po.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huangrx.template.user.base.SystemLoginUser;

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

    /**
     * 根据用户查询出所有菜单
     *
     * @param userId 用户id
     * @return 菜单列表
     */
    List<SysMenu> getMenuListByUserId(Long userId);

    /**
     * 获取系统菜单树
     *
     * @param loginUser 当前登录用户
     * @return 菜单树的列表
     */
    List<RouterDTO> getRouterTree(SystemLoginUser loginUser);
}
