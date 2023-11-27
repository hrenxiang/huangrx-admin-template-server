package com.huangrx.template.security.util;

import com.huangrx.template.security.user.base.SecurityUser;
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


    public String getCacheKey(Authentication authentication) {

    }
}
