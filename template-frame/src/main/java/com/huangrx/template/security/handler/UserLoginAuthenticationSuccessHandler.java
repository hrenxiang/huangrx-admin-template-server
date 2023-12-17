package com.huangrx.template.security.handler;

import com.huangrx.template.cache.CacheKeyEnum;
import com.huangrx.template.cache.CacheTemplate;
import com.huangrx.template.security.config.InjectionSourceConfig;
import com.huangrx.template.security.provider.token.UserLoginWeXinAuthenticationToken;
import com.huangrx.template.security.service.ILoginService;
import com.huangrx.template.security.utils.TokenUtils;
import com.huangrx.template.user.base.SystemLoginUser;
import com.huangrx.template.user.dto.TokenDTO;
import com.huangrx.template.user.vo.LoginUserVO;
import com.huangrx.template.user.vo.LoginVO;
import com.huangrx.template.utils.ServletHolderUtil;
import com.huangrx.template.utils.jackson.JacksonUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 登录成功处理
 *
 * @author huangrx
 * @since 2023-04-25 21:18
 */
@Slf4j
@Component
public class UserLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ILoginService loginService;

    public UserLoginAuthenticationSuccessHandler() {
        this.loginService = InjectionSourceConfig.instance().getLoginService();
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        TokenDTO tokenDTO = generateToken(authentication);
        SystemLoginUser loginUser = (SystemLoginUser) authentication.getPrincipal();
        // 缓存用户信息
        CacheTemplate<Object> loginUserCache = new CacheTemplate<>(CacheKeyEnum.LOGIN_USER_KEY);
        loginUserCache.set(loginUser.getCacheKey(), loginUser);
        CacheTemplate<Object> refreshTokenCache = new CacheTemplate<>(CacheKeyEnum.REFRESH_TOKEN_KEY);
        refreshTokenCache.set(loginUser.getCacheKey(), tokenDTO.getRefreshToken());
        // 解析登录用户信息
        LoginUserVO loginUserVO = new LoginUserVO();
        loginUserVO.setUserInfo(loginService.loadUserById(loginUser.getUserId()));
        loginUserVO.setRoleKey(loginUser.getRoleInfo().getRoleKey());
        loginUserVO.setPermissions(new ArrayList<>(loginUser.getRoleInfo().getMenuPermissions()));
        // 返回结果
        LoginVO result = new LoginVO();
        result.setToken(tokenDTO);
        result.setUser(loginUserVO);
        ServletHolderUtil.renderString(response, JacksonUtil.toJson(result));
        log.info("UserLoginAuthenticationSuccessHandler ------ 身份验证成功，生成Token: {}", result);
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
