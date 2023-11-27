package com.huangrx.template.security.filter;


import com.huangrx.template.cache.CacheCenter;
import com.huangrx.template.cache.RedisUtil;
import com.huangrx.template.exception.ApiException;
import com.huangrx.template.security.user.base.SecurityUser;
import com.huangrx.template.security.user.service.SystemLoginService;
import com.huangrx.template.security.user.token.TokenService;
import com.huangrx.template.security.user.token.TokenUtil;
import com.huangrx.template.security.user.web.SystemLoginUser;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


/**
 * JWT登录授权过滤器
 *
 * @author huangrx
 * @since 2023-04-26 02:19
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private SystemLoginService loginService;

    @Resource
    private CacheCenter cacheCenter;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain chain) throws ServletException, IOException {
        log.info("JwtTokenAuthenticationFilter ------ 授权校验");

        String token = TokenService.getToken(request);
        if (!StringUtils.hasLength(token)) {
            chain.doFilter(request, response);
            return;
        }
        try {
            TokenService.verifyAccessToken(token);

            SystemLoginUser loginUser = cacheCenter.loginUserCache.getObjectById(1);

            log.info("JwtTokenAuthenticationFilter ------ Token 验证成功");

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (ApiException e) {
            log.warn("JWT 解析异常:{}", e.getMessage());
        } catch (Exception e) {
            log.warn("Exception: ", e);
        } finally {
            chain.doFilter(request, response);
        }
    }

}
