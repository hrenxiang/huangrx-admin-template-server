package com.huangrx.template.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.huangrx.template.enums.BasicEnum;
import com.huangrx.template.enums.common.UserStatusEnum;
import com.huangrx.template.exception.ApiException;
import com.huangrx.template.exception.error.ErrorCode;
import com.huangrx.template.po.SysMenu;
import com.huangrx.template.po.SysRole;
import com.huangrx.template.po.SysUser;
import com.huangrx.template.security.config.TokenConfig;
import com.huangrx.template.security.service.ILoginService;
import com.huangrx.template.service.ISysMenuService;
import com.huangrx.template.service.ISysRoleService;
import com.huangrx.template.service.ISysUserService;
import com.huangrx.template.user.base.RoleInfo;
import com.huangrx.template.user.base.SystemLoginUser;
import com.huangrx.template.user.enums.DataScopeEnum;
import com.huangrx.template.user.vo.UserVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.SetUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author huangrx
 * @since 2023/11/27 14:43
 */
@Slf4j
@Service
public class LoginServiceImpl implements ILoginService {

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysMenuService menuService;

    @Resource
    private ISysRoleService roleService;


    @Override
    public SystemLoginUser loadUserByPhoneNumber(String phoneNumber) {
        SysUser userEntity = userService.loadUserByPhoneNumber(phoneNumber);
        if (Optional.ofNullable(userEntity).isEmpty()) {
            log.error("登录用户：{} 不存在.", phoneNumber);
            throw new ApiException(ErrorCode.Business.USER_NON_EXIST, phoneNumber);
        }
        if (!Objects.equals(UserStatusEnum.NORMAL.value(), userEntity.getStatus())) {
            log.error("登录用户：{} 已被停用.", phoneNumber);
            throw new ApiException(ErrorCode.Business.USER_IS_DISABLE, phoneNumber);
        }

        RoleInfo roleInfo = getRoleInfo(userEntity.getRoleId(), userEntity.getIsAdmin());

        SystemLoginUser loginUser = new SystemLoginUser(userEntity.getUserId(), userEntity.getIsAdmin(), userEntity.getUsername(),
                userEntity.getPassword(), roleInfo, userEntity.getDeptId());
        loginUser.fillLoginInfo();
        loginUser.setAutoRefreshCacheTime(loginUser.getLoginInfo().getLoginTime()
                + TimeUnit.MINUTES.toMillis(TokenConfig.getAutoRefreshTime()));
        return loginUser;
    }

    @Override
    public UserVO loadUserById(Long userId) {
        return userService.loadUserById(userId);
    }

    public RoleInfo getRoleInfo(Long roleId, boolean isAdmin) {
        if (Optional.ofNullable(roleId).isEmpty()) {
            return RoleInfo.EMPTY_ROLE;
        }

        if (isAdmin) {
            LambdaQueryWrapper<SysMenu> menuQuery = Wrappers.lambdaQuery();
            menuQuery.select(SysMenu::getMenuId);
            List<SysMenu> allMenus = menuService.list(menuQuery);

            Set<Long> allMenuIds = allMenus.stream().map(SysMenu::getMenuId).collect(Collectors.toSet());
            return new RoleInfo(RoleInfo.ADMIN_ROLE_ID, RoleInfo.ADMIN_ROLE_KEY,
                    DataScopeEnum.ALL, SetUtils.emptySet(), RoleInfo.ADMIN_PERMISSIONS, allMenuIds);
        }

        SysRole roleEntity = roleService.getById(roleId);
        if (Optional.ofNullable(roleEntity).isEmpty()) {
            return RoleInfo.EMPTY_ROLE;
        }

        List<SysMenu> menuList = menuService.getMenuListByRoleId(roleId);
        Set<Long> menuIds = menuList.stream().map(SysMenu::getMenuId).collect(Collectors.toSet());
        Set<String> permissions = menuList.stream().map(SysMenu::getPermission).collect(Collectors.toSet());
        DataScopeEnum dataScopeEnum = BasicEnum.convertByValue(DataScopeEnum.class, roleEntity.getDataScope());

        Set<Long> deptIdSet = SetUtils.emptySet();
        if (Optional.ofNullable(roleEntity.getDeptIdSet()).isPresent()) {
            deptIdSet = CharSequenceUtil.split(roleEntity.getDeptIdSet(), ",").stream()
                    .map(Convert::toLong).collect(Collectors.toSet());
        }

        return new RoleInfo(roleId, roleEntity.getRoleKey(), dataScopeEnum, deptIdSet, permissions, menuIds);
    }

}
