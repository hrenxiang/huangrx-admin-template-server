package com.huangrx.template.security.provider;

import cn.hutool.core.util.IdUtil;
import com.huangrx.template.exception.ApiException;
import com.huangrx.template.exception.error.ErrorCode;
import com.huangrx.template.po.SysUser;
import com.huangrx.template.security.config.InjectionSourceConfig;
import com.huangrx.template.security.user.base.SecurityUser;
import com.huangrx.template.security.provider.token.UserLoginNormalAuthenticationToken;
import com.huangrx.template.security.user.service.SystemLoginService;
import com.huangrx.template.security.user.web.SystemLoginUser;
import com.huangrx.template.service.ISysUserService;
import com.huangrx.template.utils.codec.CodecUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Objects;


/**
 * 用户名密码登录
 *
 * @author shansc
 */
@Slf4j
public class UserLoginNormalAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;

    private final SystemLoginService loginService;

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
        String mobile = (String) authenticationToken.getPrincipal();
        SystemLoginUser loginUser = loginService.loadUserByMobile(mobile);
        if (Objects.isNull(loginUser)) {
            throw new ApiException(ErrorCode.Business.USER_NON_EXIST);
        }

        try {
            String encodePassword = authenticationToken.getCredentials().toString();
            String decodePassword = CodecUtil.AESDecode(encodePassword, "itMCaD7HcfAnia5c");
            if (!passwordEncoder.matches(decodePassword, loginUser.getPassword())) {
                throw new ApiException(ErrorCode.Business.LOGIN_WRONG_USER_PASSWORD);
            }
        } catch (Exception e) {
            throw new ApiException(ErrorCode.Business.LOGIN_ERROR, "加解密失败");
        }

        List<GrantedAuthority> authorities = loginService.acquireUserAuthoritiesById(loginUser.getUserId());
        loginUser.setAuthorities(authorities);
        loginUser.setCacheKey(IdUtil.fastUUID());
        UserLoginNormalAuthenticationToken authenticationResult = new UserLoginNormalAuthenticationToken(loginUser, loginUser.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
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
