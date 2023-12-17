package com.huangrx.template.security.provider.token;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;



/**
 *  类说明 <br>
 *  自定义用户 login token。
 *  下一步进入 filter ，由 filter 组装认证数据传第给 认证处理器(Provider)
 *  参考 UsernamePasswordAuthenticationToken 写即可
 *
 * @author   huangrx
 * @since   2023-12-16 16:27
 */
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class UserLoginRefreshTokenAuthenticationToken extends AbstractAuthenticationToken {

    @Serial
    private static final long serialVersionUID = 520L;

    private final Object principal;

    private Object credentials;

    /**
     * 当进入 filter 后，创建了不同的 token 的构造函数
     *
     * @param principal   refreshToken
     */
    public UserLoginRefreshTokenAuthenticationToken(Object principal) {
        super(null);
        this.principal = principal;
        // 是否已经认证，false  , 在后续provider 中认证通过设置为 true
        this.setAuthenticated(false);
        log.info("UserLoginRefreshTokenAuthenticationToken setAuthenticated -> false loading ...");
    }

    /**
     * 当 token 在 provider 中通过验证，调用该构造函数创建新的 token
     *
     * @param principal   用户信息 {@link UserDetails}
     * @param authorities 用户的权限集合
     */
    public UserLoginRefreshTokenAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        // 是否已经认证，true
        super.setAuthenticated(true);
        log.info("UserLoginRefreshTokenAuthenticationToken setAuthenticated -> true loading ...");
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
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
