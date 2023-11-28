package com.huangrx.template.security.service;

import com.huangrx.template.user.base.SystemLoginUser;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * @author huangrx
 * @since 2023/11/28 10:11
 */
public interface ILoginService {

    /**
     * 加载用户名为username的用户。
     *
     * @param username 用户名
     * @return 用户
     */
    SystemLoginUser loadUserByUsername(String username);

    /**
     * 获取用户权限列表。
     *
     * @param userId 用户ID
     * @return 用户权限列表
     */
    List<GrantedAuthority> getAuthorities(Long userId);

}
