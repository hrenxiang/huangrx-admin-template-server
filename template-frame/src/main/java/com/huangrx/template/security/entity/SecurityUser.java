package com.huangrx.template.security.entity;

import cn.hutool.extra.servlet.ServletUtil;
import com.huangrx.template.utils.ServletHolderUtil;
import com.huangrx.template.utils.ip.IpRegionUtil;
import com.huangrx.template.utils.ip.IpUtil;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;


/**
 * 登录时的用户实体
 *
 * @author huangrx
 * @since 2023-04-25 22:54
 */
@Data
public class SecurityUser implements UserDetails {

    @Serial
    private static final long serialVersionUID = -4322680133914520674L;

    protected Long id;

    protected String username;

    protected String password;

    protected String mobile;

    protected String tokenType;

    protected Collection<? extends GrantedAuthority> authorities = new ArrayList<>();

    protected String userType;

    /**
     * 登录信息
     */
    protected LoginInfo loginInfo = new LoginInfo();

    public SecurityUser(Long id, String username, String password, String mobile, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.mobile = mobile;
        this.authorities = authorities;
    }

    /**
     * 设置用户代理信息
     */
    public void fillLoginInfo() {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletHolderUtil.getRequest().getHeader("User-Agent"));
        String ip = IpUtil.getClientIp(ServletHolderUtil.getRequest());

        this.getLoginInfo().setIpAddress(ip);
        this.getLoginInfo().setLocation(IpRegionUtil.getBriefLocationByIp(ip));
        this.getLoginInfo().setBrowser(userAgent.getBrowser().getName());
        this.getLoginInfo().setOperationSystem(userAgent.getOperatingSystem().getName());
        this.getLoginInfo().setLoginTime(System.currentTimeMillis());
    }


    private SecurityUser() {

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
