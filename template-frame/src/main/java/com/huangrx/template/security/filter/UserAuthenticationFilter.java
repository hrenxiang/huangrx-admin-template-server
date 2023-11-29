package com.huangrx.template.security.filter;

import com.huangrx.template.security.provider.token.UserLoginNormalAuthenticationToken;
import com.huangrx.template.security.provider.token.UserLoginWeXinAuthenticationToken;
import com.huangrx.template.user.dto.LoginDTO;
import com.huangrx.template.user.enums.LoginType;
import com.huangrx.template.utils.jackson.JacksonUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.BufferedReader;
import java.io.IOException;


/**
 * 身份验证处理过滤器
 *
 * @author huangrx
 * @since 2023-04-25 22:31
 */
@Slf4j
public class UserAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public UserAuthenticationFilter(AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler) {
        // 只处理POST /login 请求，其他的请求直接跳过
        super(new AntPathRequestMatcher("/login", HttpMethod.POST.name()));

        setAuthenticationSuccessHandler(successHandler);
        setAuthenticationFailureHandler(failureHandler);
        log.info("UserAuthenticationFilter ------ 身份认证处理过滤器初始化成功");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        log.info("UserAuthenticationFilter ------ 身份认证处理过滤器开始处理Login请求");

        // 只允许 /login 为 post 的的请求进入
        if (!request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException("认证方法不支持: " + request.getMethod());
        }

        LoginDTO requestBody = getRequestBody(request);
        AbstractAuthenticationToken authenticationToken = getAuthenticationToken(requestBody);
        authenticationToken.setDetails(authenticationDetailsSource.buildDetails(request));
        log.info("UserAuthenticationFilter ------ 身份认证处理过滤器调用相关Provider进行处理");
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }

    @NotNull
    private static AbstractAuthenticationToken getAuthenticationToken(LoginDTO requestBody) {
        String loginType = requestBody.getLoginType();
        AbstractAuthenticationToken authenticationToken;
        if (LoginType.REFRESH_TOKEN.value().equalsIgnoreCase(loginType)) {
            authenticationToken = new UserLoginNormalAuthenticationToken(requestBody.getUsername(), requestBody.getPassword());
        } else if (LoginType.WECHAT.value().equalsIgnoreCase(loginType)) {
            authenticationToken = new UserLoginWeXinAuthenticationToken(requestBody.getOpenId(), requestBody.getWxToken());
        } else {
            authenticationToken = new UserLoginNormalAuthenticationToken(requestBody.getUsername(), requestBody.getPassword());
        }
        return authenticationToken;
    }

    private LoginDTO getRequestBody(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        //将空格和换行符替换掉避免使用反序列化工具解析对象时失败
        String jsonString = sb.toString().replace("\\s", "").replace("\n", "");
        return JacksonUtil.parseString(jsonString, LoginDTO.class);
    }
}
