package com.huangrx.template.security.filter;


import com.huangrx.template.cache.CacheKeyEnum;
import com.huangrx.template.cache.CacheTemplate;
import com.huangrx.template.security.utils.TokenUtils;
import com.huangrx.template.user.base.SystemLoginUser;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;


/**
 * JWT登录授权过滤器
 *
 * @author huangrx
 * @since 2023-04-26 02:19
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private TokenUtils tokenUtils;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain chain) throws ServletException, IOException {
        log.info("JwtTokenAuthenticationFilter ------ 授权校验");
        String cacheKey = tokenUtils.getCacheKey(request);
        if (Optional.ofNullable(cacheKey).isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        CacheTemplate<Object> cacheTemplate = new CacheTemplate<>(CacheKeyEnum.LOGIN_USER_KEY);
        SystemLoginUser loginUser = (SystemLoginUser) cacheTemplate.getObjectOnlyInCacheById(cacheKey);

        if (loginUser != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 如果没有将当前登录用户放入到上下文中的话，会认定用户未授权，返回用户未登陆的错误
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

}
