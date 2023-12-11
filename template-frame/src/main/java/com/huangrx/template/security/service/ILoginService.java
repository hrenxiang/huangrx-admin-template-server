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
     * @param phoneNumber 用户名
     * @return 用户
     */
    SystemLoginUser loadUserByPhoneNumber(String phoneNumber);

}
