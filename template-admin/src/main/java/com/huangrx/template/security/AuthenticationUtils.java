package com.huangrx.template.security;

import com.huangrx.template.exception.ApiException;
import com.huangrx.template.exception.error.ErrorCode;
import com.huangrx.template.user.web.SystemLoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 安全服务工具类
 *
 * @author huangrx
 * @since 2023/11/27 23:25
 */
public class AuthenticationUtils {

    private AuthenticationUtils() {}

    /**
     * 用户ID
     **/
    public static Long getUserId() {
        try {
            return getSystemLoginUser().getUserId();
        } catch (Exception e) {
            throw new ApiException(ErrorCode.Business.USER_FAIL_TO_GET_USER_ID);
        }
    }

    /**
     * 获取系统用户
     **/
    public static SystemLoginUser getSystemLoginUser() {
        try {
            return (SystemLoginUser) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new ApiException(ErrorCode.Business.USER_FAIL_TO_GET_USER_INFO);
        }
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
