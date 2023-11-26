package com.huangrx.template.security.provider;

import cn.huangrx.blogserver.exception.ApiException;
import cn.huangrx.blogserver.exception.authentication.UserNormalLoginException;
import cn.huangrx.blogserver.module.user.entity.UserEntity;
import cn.huangrx.blogserver.module.user.service.IUserService;
import cn.huangrx.blogserver.security.config.InjectionSourceConfig;
import cn.huangrx.blogserver.security.entity.SecurityUser;
import cn.huangrx.blogserver.security.provider.token.UserLoginNormalAuthenticationToken;
import cn.huangrx.blogserver.util.CodecUtil;
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

    private final IUserService userService;

    public UserLoginNormalAuthenticationProvider() {
        this.passwordEncoder = InjectionSourceConfig.instance().getPasswordEncoder();
        this.userService = InjectionSourceConfig.instance().getUserService();
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
        UserEntity userEntity = userService.loadUserByMobile(mobile);
        if (Objects.isNull(userEntity)) {
            throw new UsernameNotFoundException(mobile);
        }

        try {
            String encodePassword = authenticationToken.getCredentials().toString();
            String decodePassword = CodecUtil.AESDecode(encodePassword, "itMCaD7HcfAnia5c");
            if (!passwordEncoder.matches(decodePassword, userEntity.getPassword())) {
                throw new UserNormalLoginException(mobile);
            }
        } catch (UserNormalLoginException | UsernameNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException("登录验证失败", e);
        }

        List<GrantedAuthority> authorities = userService.acquireUserAuthoritiesById(userEntity.getId());

        UserDetails userDetails = new SecurityUser(userEntity.getId(), userEntity.getUsername(), userEntity.getPassword(), userEntity.getMobile(), authorities);
        UserLoginNormalAuthenticationToken authenticationResult = new UserLoginNormalAuthenticationToken(userDetails, userDetails.getAuthorities());
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
