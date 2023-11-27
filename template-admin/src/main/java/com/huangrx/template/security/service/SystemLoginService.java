package com.huangrx.template.security.service;

import com.huangrx.template.user.web.SystemLoginUser;
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

    public List<GrantedAuthority> getUserAuthoritiesById(Long userId) {
        return null;
    }
}
