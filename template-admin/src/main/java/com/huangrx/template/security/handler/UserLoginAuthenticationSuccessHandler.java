package com.huangrx.template.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huangrx.template.common.CacheCenter;
import com.huangrx.template.core.dto.ResponseDTO;
import com.huangrx.template.security.provider.token.UserLoginWeXinAuthenticationToken;
import com.huangrx.template.security.service.TokenService;
import com.huangrx.template.user.token.TokenDTO;
import com.huangrx.template.user.web.SystemLoginUser;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 登录成功处理
 *
 * @author huangrx
 * @since 2023-04-25 21:18
 */
@Slf4j
@Component
public class UserLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private CacheCenter cacheCenter;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        TokenDTO data = generateToken(authentication);
        SystemLoginUser loginUser = (SystemLoginUser) authentication.getPrincipal();
        // 缓存用户信息
        cacheCenter.loginUserCache.set(loginUser.getCacheKey(), loginUser);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), ResponseDTO.success(data));
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
            return TokenService.generateTokenForUserNormal();
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
            return TokenService.generateRefreshTokenForUserNormal();
        }
    }
}
