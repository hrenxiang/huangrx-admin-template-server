package com.huangrx.template.security.user.token;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.huangrx.template.exception.ApiException;
import com.huangrx.template.exception.error.ErrorCode;
import com.huangrx.template.security.config.TokenConfig;
import com.huangrx.template.security.user.token.TokenType;
import com.huangrx.template.security.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 创建token所需方法
 *
 * @author huangrx
 * @since 2023/4/25 21:25
 */
@Slf4j
@Component
public class TokenService {

    private TokenService() {}

    /**
     * 用户名密码登录
     *
     * @param authentication 认证请求
     * @return token
     */
    public static String generateTokenForUserNormal(Authentication authentication) {
        return generateToken(authentication,TokenType.NORMAL.getCode());
    }

    /**
     * 创建刷新 Token，用户名密码登录
     *
     * @param authentication 认证请求
     * @return 生成刷新token
     */
    public static String generateRefreshTokenForUserNormal(Authentication authentication) {
        return generateRefreshToken(authentication, TokenType.NORMAL.getCode());
    }

    /**
     * 使用Auth0-Java-JWT生成对应的 Token
     *
     * @param authentication 认证请求
     * @param tokenType      Token类型 用户名密码登录，手机号验证码登录等
     * @return Token
     */
    private static String generateToken(Authentication authentication, Integer tokenType) {
        return JWT.create()
                .withSubject()
                //签发对象
                .withAudience(SecurityUtil.getMobile(authentication))
                //登录类型
                .withClaim("TOKEN_TYPE", tokenType)
                //发行时间
                .withIssuedAt(DateUtil.date().toJdkDate())
                //有效时间
                .withExpiresAt(generateExpiresDate(TokenConfig.getAccessExpireTime()))
                .sign(Algorithm.HMAC256(TokenConfig.getSecretKey()));
    }

    /**
     * 使用Auth0-Java-JWT生成对应的 RefreshToken
     *
     * @param authentication 认证请求
     * @param tokenType      Token类型 用户名密码登录，手机号验证码登录等
     * @return RefreshToken
     */
    private static String generateRefreshToken(Authentication authentication, Integer tokenType) {
        return JWT.create()
                .withSubject(TokenConfig.getTokenSubjectRefresh())
                //签发对象
                .withAudience(SecurityUtil.getMobile(authentication))
                .withClaim("TOKEN_TYPE", tokenType)
                //发行时间
                .withIssuedAt(DateUtil.date().toJdkDate())
                //有效时间
                .withExpiresAt(generateExpiresDate(TokenConfig.getRefreshExpireTime()))
                .sign(Algorithm.HMAC256(TokenConfig.getSecretKey()));
    }

    /**
     * 生成过期时间（目前固定小时，可以进行修改）
     *
     * @param time 过期时间
     * @return 过期时间
     */
    private static Date generateExpiresDate(int time) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR, time);
        return now.getTime();
    }

    /**
     * 验证 AccessToken
     *
     * @param token Token
     */
    public static void verifyAccessToken(String token) throws JWTVerificationException {
        verifyToken(token, TokenConfig.getTokenSubjectAccess());
    }

    /**
     * 验证 RefreshToken
     *
     * @param token Token
     */
    public static void verifyRefreshToken(String token) throws JWTVerificationException {
        verifyToken(token, TokenConfig.getTokenSubjectRefresh());
    }

    /**
     * 检验合法性
     *
     * @param token Token
     */
    private static void verifyToken(String token, String tokenSubject) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TokenConfig.getSecretKey()))
                .withSubject(tokenSubject)
                .build();
        try {
            verifier.verify(token);
        } catch (JWTVerificationException e) {
            log.warn("token verify error: " + e.getMessage());
            throw e;
        }
    }

    /**
     * 根据 Request 获取 Token（删除前缀后的）
     *
     * @param request Request
     * @return token（删除前缀后的）
     */
    public static String getToken(HttpServletRequest request) {
        String token = request.getHeader(TokenConfig.getTokenHeader());
        if (StringUtils.hasLength(token) && token.startsWith(TokenConfig.getTokenPrefix())) {
            return token.substring(TokenConfig.getTokenPrefix().length());
        }
        return "";
    }

    /**
     * 从 Token 获取手机号
     *
     * @param token token
     */
    public static String getAudienceMobile(String token) {
        return getAudience(token);
    }

    /**
     * 获得Audience内容
     *
     * @param token Token
     * @return Audience具体位置内容
     */
    private static String getAudience(String token) {
        try {
            return JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new ApiException(ErrorCode.Business.LOGIN_TOKEN_PARSE_ERROR);
        }
    }

    /**
     * 获取签发对象 Token Claim 中的登录类型
     *
     * @param token Token
     */
    public static String getTokenType(String token) {
        try {
            return JWT.decode(token).getClaim("TOKEN_TYPE").asString();
        } catch (JWTDecodeException j) {
            throw new ApiException(ErrorCode.Business.LOGIN_TOKEN_PARSE_ERROR);
        }
    }
}
