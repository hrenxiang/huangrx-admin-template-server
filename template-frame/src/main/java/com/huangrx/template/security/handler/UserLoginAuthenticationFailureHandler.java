package com.huangrx.template.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huangrx.template.core.dto.ResponseDTO;
import com.huangrx.template.exception.error.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
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
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ResponseDTO<Object> body = ResponseDTO.failed(ErrorCode.Business.LOGIN_ERROR);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}
