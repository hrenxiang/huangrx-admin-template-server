package com.huangrx.template.user.base;

import com.huangrx.template.utils.ServletHolderUtil;
import com.huangrx.template.utils.ip.IpRegionUtil;
import com.huangrx.template.utils.ip.IpUtil;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;


/**
 * 登录时的用户实体
 *
 * @author huangrx
 * @since 2023-04-25 22:54
 */
@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {

    @Serial
    private static final long serialVersionUID = -4322680133914520674L;

    protected Long userId;

    protected String username;

    protected String password;

    protected Collection<? extends GrantedAuthority> authorities = new ArrayList<>();

    /**
     * 用户唯一标识，缓存的key
     */
    protected String cacheKey;

    /**
     * 登录信息
     */
    private LoginInfo loginInfo = new LoginInfo();

    public LoginUser(Long userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    /**
     * 设置用户代理信息
     */
    public void fillLoginInfo() {
        UserAgent userAgent = UserAgent.parseUserAgentString(Objects.requireNonNull(ServletHolderUtil.getRequest()).getHeader("User-Agent"));
        String ip = IpUtil.getClientIp(ServletHolderUtil.getRequest());

        this.getLoginInfo().setIpAddress(ip);
        this.getLoginInfo().setLocation(IpRegionUtil.getBriefLocationByIp(ip));
        this.getLoginInfo().setBrowser(userAgent.getBrowser().getName());
        this.getLoginInfo().setOperationSystem(userAgent.getOperatingSystem().getName());
        this.getLoginInfo().setLoginTime(System.currentTimeMillis());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        // 账号是否 未过期
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 账号是否 未锁定
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 密码是 否未过期
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 是否激活
        return true;
    }
}
