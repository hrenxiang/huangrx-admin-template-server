package com.huangrx.template.security.handler;

import cn.hutool.json.JSONUtil;
import com.huangrx.template.core.dto.ResponseDTO;
import com.huangrx.template.exception.error.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * 自定义返回结果：未登录或登录过期
 *
 * @author hrenxiang
 * @since 2022-04-24 9:48 PM
 */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.parse(ResponseDTO.failed(ErrorCode.Business.LOGIN_UNAUTHORIZED)));
        response.getWriter().flush();
    }
}
