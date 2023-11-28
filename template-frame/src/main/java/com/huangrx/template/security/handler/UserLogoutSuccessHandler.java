package com.huangrx.template.security.handler;

import cn.hutool.json.JSONUtil;
import com.huangrx.template.cache.CacheKeyEnum;
import com.huangrx.template.cache.CacheTemplate;
import com.huangrx.template.core.dto.ResponseDTO;
import com.huangrx.template.user.base.SystemLoginUser;
import com.huangrx.template.security.utils.TokenUtils;
import com.huangrx.template.utils.ServletHolderUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 退出成功处理类 返回成功
 * 在SecurityConfig类当中 定义了/logout 路径对应处理逻辑
 *
 * @author huangrx
 * @since 2023/11/27 23:53
 */
@Component
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {

    @Resource
    private TokenUtils tokenUtils;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String cacheKey = tokenUtils.getCacheKey(request);
        CacheTemplate<Object> cacheTemplate = new CacheTemplate<>(CacheKeyEnum.LOGIN_USER_KEY);
        SystemLoginUser loginUser = (SystemLoginUser) cacheTemplate.getObjectOnlyInCacheByKey(cacheKey);
        if (loginUser != null) {
            // 删除用户缓存记录
            cacheTemplate.del(loginUser.getCacheKey());
        }
        ServletHolderUtil.renderString(response, JSONUtil.toJsonStr(ResponseDTO.success()));
    }
}
