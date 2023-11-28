package com.huangrx.template.security.handler;

import com.huangrx.template.core.dto.ResponseDTO;
import com.huangrx.template.exception.ApiException;
import com.huangrx.template.exception.error.ErrorCode;
import com.huangrx.template.utils.ServletHolderUtil;
import com.huangrx.template.utils.jackson.JacksonUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * 自定义返回结果：未登录或登录过期
 *
 * @author hrenxiang
 * @since 2022-04-24 9:48 PM
 */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        ServletHolderUtil.renderString(response, JacksonUtil.toJson(ResponseDTO.failed(new ApiException(ErrorCode.Client.COMMON_NO_AUTHORIZATION, request.getRequestURI()))));
    }
}
