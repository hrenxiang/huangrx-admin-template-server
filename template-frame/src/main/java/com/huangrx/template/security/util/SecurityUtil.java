package com.huangrx.template.security.util;

import com.huangrx.template.security.entity.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;

/**
 * 主要用来处理 token中的信息
 *
 * @author huangrx
 * @since 2023/4/25 21:54
 */
public class SecurityUtil {

    /**
     * 获取当前用户名
     *
     * @return 用户名
     */
    public static String getMobile(Authentication authentication) {
        if (Objects.isNull(authentication)) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((SecurityUser) principal).getMobile();
        } else if (principal != null) {
            return principal.toString();
        }

        return null;
    }

    /**
     * 获取当前用户手机号
     * @return 用户名
     */
    public static String getMobile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getMobile(authentication);
    }
}
