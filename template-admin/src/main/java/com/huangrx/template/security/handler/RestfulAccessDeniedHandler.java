package com.huangrx.template.security.handler;

import com.huangrx.template.core.dto.ResponseDTO;
import com.huangrx.template.exception.error.ErrorCode;
import com.huangrx.template.utils.ServletHolderUtil;
import com.huangrx.template.utils.jackson.JacksonUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 自定义返回结果：没有权限访问时
 *
 * @author hrenxiang
 * @since 2022-04-24 9:48 PM
 */
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) {
        ServletHolderUtil.renderString(response, JacksonUtil.toJson(ResponseDTO.failed(ErrorCode.Client.COMMON_NO_AUTHORIZATION)));
    }
}
