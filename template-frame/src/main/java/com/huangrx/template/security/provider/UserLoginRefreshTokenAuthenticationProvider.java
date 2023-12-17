package com.huangrx.template.security.provider;

import cn.hutool.core.util.IdUtil;
import com.huangrx.template.cache.CacheKeyEnum;
import com.huangrx.template.cache.CacheTemplate;
import com.huangrx.template.exception.ApiException;
import com.huangrx.template.exception.error.ErrorCode;
import com.huangrx.template.security.config.InjectionSourceConfig;
import com.huangrx.template.security.provider.token.UserLoginRefreshTokenAuthenticationToken;
import com.huangrx.template.security.service.ILoginService;
import com.huangrx.template.security.utils.TokenUtils;
import com.huangrx.template.user.base.SystemLoginUser;
import com.huangrx.template.user.enums.TokenType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;


/**
 * refreshToken登录
 *
 * @author shansc
 */
@Slf4j
public class UserLoginRefreshTokenAuthenticationProvider implements AuthenticationProvider {

    private final ILoginService loginService;

    public UserLoginRefreshTokenAuthenticationProvider() {
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
        String token = (String) authentication.getPrincipal();
        // 校验 refreshToken
        String cacheKey = TokenUtils.getCacheKeyFromToken(token, TokenType.REFRESH_TOKEN.value());
        CacheTemplate<Object> refreshTokenCache = new CacheTemplate<>(CacheKeyEnum.REFRESH_TOKEN_KEY);
        String refreshTokenInCache = (String) refreshTokenCache.getObjectOnlyInCacheById(cacheKey);
        if (Optional.ofNullable(refreshTokenInCache).isEmpty()) {
            throw new ApiException(ErrorCode.Business.LOGIN_UNAUTHORIZED);
        }
        // 移除 当前refreshToken，后续创建新的
        refreshTokenCache.del(cacheKey);
        // 获取用户信息
        String username = TokenUtils.getAudienceUsername(token);
        SystemLoginUser loginUser = loginService.loadUserByPhoneNumber(username);
        // 设置缓存的KEY
        loginUser.setCacheKey(IdUtil.fastUUID());
        UserLoginRefreshTokenAuthenticationToken authenticationResult = new UserLoginRefreshTokenAuthenticationToken(loginUser, loginUser.getAuthorities());
        authenticationResult.setDetails(authentication.getDetails());
        // 把当前登录用户 放入上下文中
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("UserLoginRefreshTokenAuthenticationProvider ------ 处理无感刷新登录请求完成");
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
        return UserLoginRefreshTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
