package com.huangrx.template.service.impl;

import com.huangrx.template.security.service.ILoginService;
import com.huangrx.template.user.base.SystemLoginUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huangrx
 * @since 2023/11/27 14:43
 */
@Service
public class LoginServiceImpl implements ILoginService {


    @Override
    public SystemLoginUser loadUserByUsername(String username) {
        return null;
    }

    @Override
    public List<GrantedAuthority> getAuthorities(Long userId) {
        return null;
    }

}
