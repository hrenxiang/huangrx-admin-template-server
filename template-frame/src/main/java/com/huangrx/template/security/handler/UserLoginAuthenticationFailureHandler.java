package com.huangrx.template.security.handler;

import com.huangrx.template.core.dto.ResponseDTO;
import com.huangrx.template.exception.ApiException;
import com.huangrx.template.exception.error.ErrorCode;
import com.huangrx.template.utils.ServletHolderUtil;
import com.huangrx.template.utils.jackson.JacksonUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 登录失败处理
 *
 * @author huangrx
 * @since 2023-04-25 21:17
 */
@Component
public class UserLoginAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        ServletHolderUtil.renderString(response, JacksonUtil.toJson(ResponseDTO.failed(new ApiException(ErrorCode.Business.LOGIN_ERROR))));
    }
}
