package com.huangrx.template.security.provider.token;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 自定义用户 login token。
 * 参考 UsernamePasswordAuthenticationToken。
 * 下一步进入 filter ，由 filter 组装认证数据传第给 认证处理器(Provider)。
 *
 * @author huangrx
 * @since 2023-04-26 13:18
 */
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class UserLoginNormalAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;

    private Object credentials;

    /**
     * 当进入 filter 后，创建了不同的 token 的构造函数
     *
     * @param principal   username
     * @param credentials password
     */
    public UserLoginNormalAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        // 是否已经认证，false  , 在后续provider 中认证通过设置为 true
        this.setAuthenticated(false);
        log.info("UserLoginNormalAuthenticationToken ------ 认证前构造AuthenticationToken信息");
    }


    /**
     * 当 token 在 provider 中通过验证，调用该构造函数创建新的 token
     *
     * @param principal   用户信息 {@link UserDetails}
     * @param authorities 用户的权限集合
     */
    public UserLoginNormalAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
        log.info("UserLoginNormalAuthenticationToken ------ 认证完成调用");
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }


}
