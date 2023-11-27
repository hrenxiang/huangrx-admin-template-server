package com.huangrx.template.security.handler;

import cn.hutool.json.JSONUtil;
import com.huangrx.template.core.dto.ResponseDTO;
import com.huangrx.template.exception.error.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * 自定义返回结果：没有权限访问时
 *
 * @author hrenxiang
 * @since 2022-04-24 9:48 PM
 */
public class RestfulAccessDeniedHandler implements AccessDeniedHandler{
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.parse(ResponseDTO.failed(ErrorCode.Business.PERMISSION_NOT_ALLOWED_TO_OPERATE)));
        response.getWriter().flush();
    }
}
