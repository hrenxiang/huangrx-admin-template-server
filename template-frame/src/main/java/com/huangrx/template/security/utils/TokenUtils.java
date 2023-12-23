package com.huangrx.template.security.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.huangrx.template.exception.ApiException;
import com.huangrx.template.exception.error.ErrorCode;
import com.huangrx.template.security.config.TokenConfig;
import com.huangrx.template.user.enums.TokenType;
import com.huangrx.template.user.base.SystemLoginUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 创建token所需方法
 *
 * @author huangrx
 * @since 2023/4/25 21:25
 */
@Slf4j
@Component
public class TokenUtils {

    private TokenUtils() {
    }

    /**
     * 用户名密码登录
     *
     * @return 生成token
     */
    public static String generateTokenForUserNormal() {
        return generateToken(TokenType.NORMAL);
    }

    /**
     * 创建刷新 Token，用户名密码登录
     *
     * @return 生成刷新token
     */
    public static String generateRefreshTokenForUserNormal() {
        return generateRefreshToken(TokenType.NORMAL);
    }

    /**
     * 微信登录
     *
     * @return 生成token
     */
    public static String generateTokenForWeChat() {
        return generateToken(TokenType.WECHAT);
    }

    /**
     * 创建刷新 Token，微信登录
     *
     * @return 生成刷新token
     */
    public static String generateRefreshTokenForWeChat() {
        return generateRefreshToken(TokenType.WECHAT);
    }

    /**
     * 使用Auth0-Java-JWT生成对应的 Token
     *
     * @param tokenType Token类型 用户名密码登录，手机号验证码登录等
     * @return Token
     */
    private static String generateToken(TokenType tokenType) {
        return JWT.create()
                // 签发类型
                .withSubject(TokenConfig.getTokenSubjectAccess())
                //签发对象
                .withAudience(SecurityUtils.getSystemLoginUser().getUsername())
                //登录类型
                .withClaim(TokenConfig.getTokenTypeKey(), tokenType.value())
                // 用户信息缓存的key值
                .withClaim(TokenConfig.getLoginUserKey(), SecurityUtils.getSystemLoginUser().getCacheKey())
                //发行时间
                .withIssuedAt(DateUtil.date().toJdkDate())
                //有效时间
                .withExpiresAt(generateTokenExpireDate())
                .sign(Algorithm.HMAC256(TokenConfig.getSecretKey()));
    }

    /**
     * 使用Auth0-Java-JWT生成对应的 RefreshToken
     *
     * @param tokenType Token类型 用户名密码登录，手机号验证码登录等
     * @return RefreshToken
     */
    private static String generateRefreshToken(TokenType tokenType) {
        return JWT.create()
                // 签发类型
                .withSubject(TokenConfig.getTokenSubjectRefresh())
                //签发对象
                .withAudience(SecurityUtils.getSystemLoginUser().getUsername())
                //登录类型
                .withClaim(TokenConfig.getTokenTypeKey(), tokenType.value())
                // 用户信息缓存的key值
                .withClaim(TokenConfig.getLoginUserKey(), SecurityUtils.getSystemLoginUser().getCacheKey())
                //发行时间
                .withIssuedAt(DateUtil.date().toJdkDate())
                //有效时间
                .withExpiresAt(generateRefreshTokenExpireDate())
                .sign(Algorithm.HMAC256(TokenConfig.getSecretKey()));
    }

    /**
     * 生成访问刷新TOKEN过期时间(JWT使用UTC时间，即1970年1月1日0时0分0秒至今的秒数， +8小时为北京时间)
     *
     * @return 刷新token过期时间
     */
    @NotNull
    public static Date generateRefreshTokenExpireDate() {
        return generateExpiresDate(Calendar.HOUR, TokenConfig.getRefreshExpireTime() + 8);
    }

    /**
     * 生成访问TOKEN过期时间(JWT使用UTC时间，即1970年1月1日0时0分0秒至今的秒数， +8小时为北京时间)
     *
     * @return token过期时间
     */
    @NotNull
    public static Date generateTokenExpireDate() {
        return generateExpiresDate(Calendar.MINUTE, TokenConfig.getAccessExpireTime() + 8);
    }


    /**
     * 生成过期时间
     *
     * @param field 时间单位
     * @param time  过期时间
     * @return 过期时间
     */
    private static Date generateExpiresDate(int field, int time) {
        Calendar now = Calendar.getInstance();
        now.add(field, time);
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
     * 获取用户身份信息缓存KEY
     *
     * @return 用户信息缓存KEY
     */
    public String getCacheKey(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        return getCacheKeyFromToken(token, TokenType.NORMAL.getValue());
    }

    /**
     * 获取用户身份信息缓存KEY
     *
     * @return 用户信息缓存KEY
     */
    @Nullable
    public static String getCacheKeyFromToken(String token, Integer tokenType) {
        if (CharSequenceUtil.isNotEmpty(token)) {
            try {
                // 验证Token有效性
                if (TokenType.REFRESH_TOKEN.value().equals(tokenType)) {
                    verifyRefreshToken(token);
                } else {
                    verifyAccessToken(token);
                }
                // 解析Token获取Claims
                Map<String, Claim> claims = JWT.decode(token).getClaims();
                // 解析对应的权限以及用户信息
                return claims.get(TokenConfig.getLoginUserKey()).asString();
            } catch (IllegalArgumentException jwtException) {
                log.error("parse token failed.", jwtException);
                throw new ApiException(jwtException, ErrorCode.Client.INVALID_TOKEN);
            } catch (Exception e) {
                log.error("fail to get cached user from redis", e);
                throw new ApiException(e, ErrorCode.Client.TOKEN_PROCESS_FAILED, e.getMessage());
            }

        }
        return null;
    }

    /**
     * 获取签发对象：用户手机号
     *
     * @param token token
     * @return 用户手机号
     */
    public static String getAudienceUsername(String token) {
        return getAudience(token, 0);
    }

    /**
     * 获得Audience内容
     *
     * @param token token
     * @param key   数据对应下标
     * @return Audience内容
     */
    private static String getAudience(String token, Integer key) {
        try {
            return JWT.decode(token).getAudience().get(key);
        } catch (JWTDecodeException j) {
            //这里是token解析失败
            throw new ApiException(ErrorCode.Client.INVALID_TOKEN);
        }
    }
}
