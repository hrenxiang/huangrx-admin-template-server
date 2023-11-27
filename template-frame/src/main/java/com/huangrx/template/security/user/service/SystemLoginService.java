package com.huangrx.template.security.user.service;

import com.huangrx.template.po.SysUser;
import com.huangrx.template.security.user.web.SystemLoginUser;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * @author huangrx
 * @since 2023/11/27 14:43
 */
public class SystemLoginService {

    public SystemLoginUser loadUserByMobile(String mobile) {
        return null;
    }

    public List<GrantedAuthority> acquireUserAuthoritiesById(Long userId) {
        return null;
    }
}
