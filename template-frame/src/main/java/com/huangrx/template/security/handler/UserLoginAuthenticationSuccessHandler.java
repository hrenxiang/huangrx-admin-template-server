package com.huangrx.template.security.handler;

import com.huangrx.template.cache.CacheKeyEnum;
import com.huangrx.template.cache.CacheTemplate;
import com.huangrx.template.security.provider.token.UserLoginWeXinAuthenticationToken;
import com.huangrx.template.security.utils.TokenUtils;
import com.huangrx.template.user.base.SystemLoginUser;
import com.huangrx.template.user.dto.TokenDTO;
import com.huangrx.template.utils.ServletHolderUtil;
import com.huangrx.template.utils.jackson.JacksonUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 登录成功处理
 *
 * @author huangrx
 * @since 2023-04-25 21:18
 */
@Slf4j
@Component
public class UserLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        TokenDTO data = generateToken(authentication);
        SystemLoginUser loginUser = (SystemLoginUser) authentication.getPrincipal();
        // 缓存用户信息
        CacheTemplate<Object> cacheTemplate = new CacheTemplate<>(CacheKeyEnum.LOGIN_USER_KEY);
        cacheTemplate.set(loginUser.getCacheKey(), loginUser);
        ServletHolderUtil.renderString(response, JacksonUtil.toJson(data));
        log.info("UserLoginAuthenticationSuccessHandler ------ 身份验证成功，生成Token: {}", data);
    }

    /**
     * 生成token数据{@link TokenDTO}
     *
     * @param authentication 认证请求
     */
    private TokenDTO generateToken(Authentication authentication) {
        return TokenDTO.builder()
                .accessToken(generateAccessToken(authentication))
                .refreshToken(generateRefreshToken(authentication))
                .expire(TokenUtils.generateTokenExpireDate())
                .build();
    }

    /**
     * 创建 Token
     *
     * @param authentication 认证请求
     * @return Token
     */
    private String generateAccessToken(Authentication authentication) {
        if (authentication instanceof UserLoginWeXinAuthenticationToken) {
            return null;
        } else {
            return TokenUtils.generateTokenForUserNormal();
        }
    }

    /**
     * 创建 RefreshToken
     *
     * @param authentication 认证请求
     * @return RefreshToken
     */
    private String generateRefreshToken(Authentication authentication) {
        if (authentication instanceof UserLoginWeXinAuthenticationToken) {
            return null;
        } else {
            return TokenUtils.generateRefreshTokenForUserNormal();
        }
    }
}
