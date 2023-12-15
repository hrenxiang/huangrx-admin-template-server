package com.huangrx.template.security.service;

import com.huangrx.template.user.base.SystemLoginUser;
import com.huangrx.template.user.vo.UserVO;
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

    /**
     * 加载用户ID为userId的用户
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserVO loadUserById(Long userId);
}
