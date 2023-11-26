package com.huangrx.template.security.handler;

import cn.huangrx.blogserver.redis.CachePrefix;
import cn.huangrx.blogserver.redis.service.RedisService;
import cn.huangrx.blogserver.response.BaseResponse;
import cn.huangrx.blogserver.security.config.TokenConfig;
import cn.huangrx.blogserver.security.provider.token.UserLoginWeXinAuthenticationToken;
import cn.huangrx.blogserver.security.util.SecurityUtil;
import cn.huangrx.blogserver.security.util.TokenUtil;
import cn.huangrx.blogserver.security.vo.TokenVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("UserLoginAuthenticationSuccessHandler ------ 身份验证成功，生成Token");
        TokenVO data = generateToken(authentication);
        redisService.set(CachePrefix.ACCESS.getPrefix() + SecurityUtil.getMobile(authentication), data.getAccessToken(), TokenConfig.getAccessExpireTime() * 60 * 60);
        redisService.set(CachePrefix.REFRESH_ACCESS.getPrefix() + SecurityUtil.getMobile(authentication), data.getAccessToken(), TokenConfig.getRefreshExpireTime() * 60 * 60);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), BaseResponse.success(data));
        log.info("UserLoginAuthenticationSuccessHandler ------ 身份验证成功，生成Token完成");
    }


    /**
     * 生成token数据{@link TokenVO}
     *
     * @param authentication 认证请求
     * @return token
     */
    private TokenVO generateToken(Authentication authentication) {
        return TokenVO.builder()
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
            return TokenUtil.generateTokenForWeiXin(authentication);
        } else {
            return TokenUtil.generateTokenForUserNormal(authentication);
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
            return TokenUtil.generateRefreshTokenForWeiXin(authentication);
        } else {
            return TokenUtil.generateRefreshTokenForUserNormal(authentication);
        }
    }
}
