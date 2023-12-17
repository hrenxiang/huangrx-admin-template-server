package com.huangrx.template.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.huangrx.template.convert.RouterConvert;
import com.huangrx.template.dto.RouterDTO;
import com.huangrx.template.enums.common.StatusEnum;
import com.huangrx.template.po.SysMenu;
import com.huangrx.template.mapper.SysMenuMapper;
import com.huangrx.template.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huangrx.template.user.base.SystemLoginUser;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author huangrx
 * @since 2023-11-26
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Override
    public List<SysMenu> getMenuListByRoleId(Long roleId) {
        return baseMapper.getMenuListByRoleId(roleId);
    }

    @Override
    public List<SysMenu> getMenuListByUserId(Long userId) {
        return baseMapper.getMenuListByUserId(userId);
    }

    @Override
    public List<RouterDTO> getRouterTree(SystemLoginUser loginUser) {
        List<Tree<Long>> trees = buildMenuEntityTree(loginUser);
        return buildRouterTree(trees);
    }

    public List<RouterDTO> buildRouterTree(List<Tree<Long>> trees) {
        List<RouterDTO> routers = new LinkedList<>();
        if (CollUtil.isNotEmpty(trees)) {
            for (Tree<Long> tree : trees) {
                Object entity = tree.get("entity");
                if (entity != null) {
                    RouterDTO routerDTO = RouterConvert.INSTANCE.sysMenuToRouterDto((SysMenu) entity);
                    List<Tree<Long>> children = tree.getChildren();
                    if (CollUtil.isNotEmpty(children)) {
                        routerDTO.setChildren(buildRouterTree(children));
                    }
                    routers.add(routerDTO);
                }

            }
        }
        return routers;
    }

    public List<Tree<Long>> buildMenuEntityTree(SystemLoginUser loginUser) {
        List<SysMenu> allMenus;
        if (loginUser.isAdmin()) {
            allMenus = this.list();
        } else {
            allMenus = this.getMenuListByUserId(loginUser.getUserId());
        }

        // 传给前端的路由排除掉按钮和停用的菜单
        List<SysMenu> noButtonMenus = allMenus.stream()
                .filter(menu -> !menu.getIsButton())
                .filter(menu-> StatusEnum.ENABLE.value().equals(menu.getStatus()))
                .toList();

        TreeNodeConfig config = new TreeNodeConfig();
        // 默认为id 可以不设置
        config.setIdKey("menuId");

        return TreeUtil.build(noButtonMenus, 0L, config, (menu, tree) -> {
            // 也可以使用 tree.setId(dept.getId());等一些默认值
            tree.setId(menu.getMenuId());
            tree.setParentId(menu.getParentId());
            // 也可以使用权重来排序 <p>tree.setWeight(menu.getRank());</p>
            tree.putExtra("entity", menu);
        });

    }
}
