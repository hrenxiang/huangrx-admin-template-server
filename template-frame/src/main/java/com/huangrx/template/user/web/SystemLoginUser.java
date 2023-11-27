package com.huangrx.template.user.web;

import com.huangrx.template.user.base.SecurityUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * 登录用户身份权限
 *
 * @author huangrx
 * @since 2023/11/27 14:18
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SystemLoginUser extends SecurityUser {

    @Serial
    private static final long serialVersionUID = 1L;

    private boolean isAdmin;

    private Long deptId;

    private RoleInfo roleInfo;

    /**
     * 当超过这个时间 则触发刷新缓存时间
     */
    private Long autoRefreshCacheTime;

    public SystemLoginUser(Long userId, Boolean isAdmin, String username, String password, RoleInfo roleInfo, Long deptId) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.deptId = deptId;
        this.roleInfo = roleInfo;
    }

    public SystemLoginUser(Long userId, String username, String password) {
        super(userId, username, password);
    }

    public Long getRoleId() {
        return getRoleInfo().getRoleId();
    }

}
