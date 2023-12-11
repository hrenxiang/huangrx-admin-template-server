package com.huangrx.template.security.config;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 请求 Token中包含字段枚举
 *
 * @author huangrx
 * @since 2023/4/26 12:48
 */
@Slf4j
@Data
@Component
public class TokenConfig {

    private TokenConfig() {}

    @Getter
    private static String tokenHeader;

    @Getter
    private static String tokenPrefix;

    @Getter
    private static String tokenSubjectAccess;

    @Getter
    private static String tokenSubjectRefresh;

    @Getter
    private static String secretKey;

    @Getter
    private static Integer accessExpireTime;

    @Getter
    private static Integer refreshExpireTime;

    @Getter
    private static String loginUserKey;

    @Getter
    private static String tokenTypeKey;

    @Getter
    private static Long autoRefreshTime;

    @Value("${authorization.token-header}")
    private void setTokenHeader(String tokenHeader) {
        TokenConfig.tokenHeader = tokenHeader;
    }

    @Value("${authorization.token-prefix}")
    private void setTokenPrefix(String tokenPrefix) {
        TokenConfig.tokenPrefix = tokenPrefix;
    }

    @Value("${authorization.token-subject-access}")
    private void setTokenSubjectAccess(String tokenSubjectAccess) {
        TokenConfig.tokenSubjectAccess = tokenSubjectAccess;
    }

    @Value("${authorization.token-subject-refresh}")
    private void setTokenSubjectRefresh(String tokenSubjectRefresh) {
        TokenConfig.tokenSubjectRefresh = tokenSubjectRefresh;
    }

    @Value("${authorization.secret-key}")
    private void setSecretKey(String secretKey) {
        TokenConfig.secretKey = secretKey;
    }

    @Value("${authorization.access-expire-time}")
    private void setAccessExpireTime(Integer accessExpireTime) {
        TokenConfig.accessExpireTime = accessExpireTime;
    }

    @Value("${authorization.refresh-expire-time}")
    private void setRefreshExpireTime(Integer refreshExpireTime) {
        TokenConfig.refreshExpireTime = refreshExpireTime;
    }

    @Value("${authorization.login-user-key}")
    private void setLoginUserKey(String loginUserKey) {
        TokenConfig.loginUserKey = loginUserKey;
    }

    @Value("${authorization.token-type-key}")
    private void setTokenTypeKey(String tokenTypeKey) {
        TokenConfig.tokenTypeKey = tokenTypeKey;
    }

    @Value("${authorization.auto-refresh-time}")
    private void setAutoRefreshTime(Long autoRefreshTime) {
        TokenConfig.autoRefreshTime = autoRefreshTime;
    }
}
