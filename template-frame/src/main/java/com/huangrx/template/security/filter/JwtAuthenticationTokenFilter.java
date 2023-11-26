//package com.huangrx.template.security.filter;
//
//
//import com.huangrx.template.exception.ApiException;
//import com.huangrx.template.security.entity.SecurityUser;
//import com.huangrx.template.security.util.TokenUtil;
//import jakarta.annotation.Resource;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.List;
//
//
///**
// * JWT登录授权过滤器
// *
// * @author huangrx
// * @since 2023-04-26 02:19
// */
//@Slf4j
//public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
//
//    @Resource
//    private  userService;
//
//    @Resource
//    private RedisService redisService;
//
//    @Override
//    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
//                                    @NotNull FilterChain chain) throws ServletException, IOException {
//        log.info("JwtTokenAuthenticationFilter ------ 授权校验");
//
//        String token = TokenUtil.getToken(request);
//        if (!StringUtils.hasLength(token)) {
//            chain.doFilter(request, response);
//            return;
//        }
//        try {
//            TokenUtil.verifyAccessToken(token);
//            String mobile = TokenUtil.getAudienceMobile(token);
//            String tokenType = TokenUtil.getTokenType(token);
//            UserEntity userEntity = userService.loadUserByMobile(mobile);
//            List<GrantedAuthority> authorities = userService.acquireUserAuthoritiesById(userEntity.getId());
//
//            SecurityUser user = new SecurityUser(userEntity.getId(), userEntity.getUsername(), userEntity.getPassword(), userEntity.getMobile(), authorities);
//            user.setTokenType(tokenType);
//
//            // 添加缓存 进行校验 Token是否过期
//            String accessToken = redisService.get(CachePrefix.ACCESS.getPrefix() + mobile).toString();
//            if (!StringUtils.hasLength(accessToken)) {
//                throw new ApiException(String.format("缓存中 %s 的Token不存在", mobile));
//            }
//            log.info("JwtTokenAuthenticationFilter ------ Token 验证成功");
//
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        } catch (ApiException e) {
//            log.warn("JWT 解析异常:{}", e.getMessage());
//        } catch (Exception e) {
//            log.warn("Exception: ", e);
//        } finally {
//            chain.doFilter(request, response);
//        }
//    }
//
//}