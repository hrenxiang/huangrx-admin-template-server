package com.huangrx.template.mapper;

import com.huangrx.template.po.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author huangrx
 * @since 2023-11-26
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据角色ID查询对应的菜单权限
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    @Select("SELECT m.* "
            + "FROM t_sys_menu m "
            + " LEFT JOIN t_sys_role_menu rm ON m.menu_id = rm.menu_id "
            + " LEFT JOIN t_sys_role r ON r.role_id = rm.role_id "
            + "WHERE m.status = 1 AND m.deleted = 0 "
            + " AND r.status = 1 AND r.deleted = 0 "
            + " AND r.role_id = #{roleId}")
    List<SysMenu> getMenuListByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据用户查询出所有菜单
     *
     * @param userId 用户id
     * @return 菜单列表
     */
    @Select("SELECT DISTINCT m.* "
            + "FROM sys_menu m "
            + " LEFT JOIN t_sys_role_menu rm ON m.menu_id = rm.menu_id "
            + " LEFT JOIN t_sys_user u ON rm.role_id = u.role_id "
            + "WHERE u.user_id = #{userId} "
            + " AND m.status = 1 "
            + " AND m.deleted = 0 "
            + "ORDER BY m.parent_id")
    List<SysMenu> getMenuListByUserId(@Param("userId")Long userId);
}
