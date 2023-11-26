package com.huangrx.template.security.provider.token;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


/**
 * 微信授权登录
 *
 * @author shansc
 */
@Slf4j
@Setter
@Getter
public class UserLoginWeXinAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 520L;

    private final Object principal;

    private Object credentials;
    private String mobile;

    /**
     * 当进入 filter 后，创建了不同的 token 的构造函数
     *
     * @param principal openid
     * @param credentials token
     */
    public UserLoginWeXinAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(false); // 是否已经认证，false  , 在后续provider 中认证通过设置为 true
        log.debug("UserLoginWechatAuthenticationToken setAuthenticated ->false loading ...");
    }

    /**
     * 当 token 在 provider 中通过验证，调用该构造函数创建新的 token
     *
     * @param principal   用户信息 {@link UserDetails}
     * @param authorities 用户的权限集合
     */
    public UserLoginWeXinAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true); // 是否已经认证，true
        log.debug("UserLoginWechatAuthenticationToken setAuthenticated ->true loading ...");
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
