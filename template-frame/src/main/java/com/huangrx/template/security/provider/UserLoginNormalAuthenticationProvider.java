package com.huangrx.template.security.provider;

import cn.hutool.core.util.IdUtil;
import com.huangrx.template.exception.ApiException;
import com.huangrx.template.exception.error.ErrorCode;
import com.huangrx.template.security.config.InjectionSourceConfig;
import com.huangrx.template.security.provider.token.UserLoginNormalAuthenticationToken;
import com.huangrx.template.security.service.ILoginService;
import com.huangrx.template.user.base.SystemLoginUser;
import com.huangrx.template.utils.codec.CodecUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * 用户名密码登录
 *
 * @author huangrx
 * @since 2023/11/28 11:28
 */
@Slf4j
public class UserLoginNormalAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;

    private final ILoginService loginService;

    public UserLoginNormalAuthenticationProvider() {
        this.passwordEncoder = InjectionSourceConfig.instance().getPasswordEncoder();
        this.loginService = InjectionSourceConfig.instance().getLoginService();
    }


    /**
     * 认证
     *
     * @param authentication 用户登录的认证信息
     * @return 认证结果
     * @throws AuthenticationException 认证异常
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("UserLoginNormalAuthenticationProvider ------ 处理用户名密码登录请求");
        UserLoginNormalAuthenticationToken authenticationToken = (UserLoginNormalAuthenticationToken) authentication;
        String username = (String) authenticationToken.getPrincipal();
        SystemLoginUser loginUser = loginService.loadUserByPhoneNumber(username);

        // 密码目前使用AES算法加密后传输，可逆，这里需要解密后再配置的passwordEncoder使用进行加密处理
        String encodePassword = authenticationToken.getCredentials().toString();
        String decodePassword = CodecUtil.aesDecode(encodePassword, "itMCaD7HcfAnia5c");
        if (!passwordEncoder.matches(decodePassword, loginUser.getPassword())) {
            throw new ApiException(ErrorCode.Business.LOGIN_WRONG_USER_PASSWORD);
        }

        // 设置权限
        Set<String> menuPermissions = loginUser.getRoleInfo().getMenuPermissions();
        List<GrantedAuthority> authorities = new ArrayList<>(menuPermissions.size());
        for (String authority : menuPermissions) {
            authorities.add(new SimpleGrantedAuthority(authority));
        }
        loginUser.setAuthorities(authorities);
        // 设置缓存的KEY
        loginUser.setCacheKey(IdUtil.fastUUID());
        UserLoginNormalAuthenticationToken authenticationResult = new UserLoginNormalAuthenticationToken(loginUser, loginUser.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        // 把当前登录用户 放入上下文中
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("UserLoginNormalAuthenticationProvider ------ 处理用户名密码登录请求完成");
        return authenticationResult;
    }

    /**
     * 由 提供给 spring-security AuthenticationManager <br>
     * 根据 token 类型，判断使用那个 Provider
     *
     * @param authentication 提供manager 识别认证器
     * @return 是否匹配
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UserLoginNormalAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
