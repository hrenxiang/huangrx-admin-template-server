package com.huangrx.template.security.provider;

import cn.huangrx.blogserver.exception.ApiException;
import cn.huangrx.blogserver.module.user.entity.UserEntity;
import cn.huangrx.blogserver.module.user.enums.WeiXinUrlEnum;
import cn.huangrx.blogserver.module.user.service.IUserService;
import cn.huangrx.blogserver.module.user.vo.WeiXinBaseVO;
import cn.huangrx.blogserver.security.config.InjectionSourceConfig;
import cn.huangrx.blogserver.security.entity.SecurityUser;
import cn.huangrx.blogserver.security.provider.token.UserLoginWeXinAuthenticationToken;
import cn.huangrx.blogserver.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;


/**
 * 微信授权登录
 *
 * @author shansc
 */
@Slf4j
public class UserLoginWeXinAuthenticationProvider implements AuthenticationProvider {
    private final IUserService userService;
    private final RestTemplate requester;

    public UserLoginWeXinAuthenticationProvider(RestTemplate requester) {
        this.userService = InjectionSourceConfig.instance().getUserService();
        this.requester = requester;
    }

    /**
     * 认证
     *
     * @param authentication 用户登录的认证信息
     * @return 认证结果
     * @throws AuthenticationException 认证异常
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserLoginWeXinAuthenticationToken auth = (UserLoginWeXinAuthenticationToken) authentication;
        String openId = (String) auth.getPrincipal();
        String accessToken = (String) auth.getCredentials();

        // 验证微信登录是否合法
        if (!verifyWechatLogin(openId, accessToken)) {
            throw new ApiException("微信登录失败");
        }

        UserEntity userEntity = userService.loadUserByWechatOpenId(openId);

        if (Objects.isNull(userEntity)) {
            throw new ApiException("未注册");
        }

        List<GrantedAuthority> authorities = userService.acquireUserAuthoritiesById(userEntity.getId());

        SecurityUser user = new SecurityUser(userEntity.getId(), userEntity.getUsername(), userEntity.getPassword(), userEntity.getMobile(), authorities);;

        UserLoginWeXinAuthenticationToken authenticationResult = new UserLoginWeXinAuthenticationToken(user, ((UserDetails) user).getAuthorities());
        authenticationResult.setDetails(authentication.getDetails());
        return authenticationResult;
    }


    /**
     * 检验授权凭证（access_token）是否有效
     *
     * @param openId      openId
     * @param accessToken accessToken
     * @return true-合法，false-不合法
     */
    public boolean verifyWechatLogin(String openId, String accessToken) {
        String url = String.format(WeiXinUrlEnum.AUTH.getUrl(), accessToken, openId);
        try {
            String resultStr = requester.getForObject(url, String.class);
            WeiXinBaseVO result = JacksonUtil.parseString(resultStr, WeiXinBaseVO.class);
            if (Objects.isNull(result)) {
                return Boolean.FALSE;
            }
            return result.getErrcode() == 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 由 提供给 spring-security AuthenticationManager <br>
     * 根据 token 类型，判断使用那个 Provider
     *
     * @param authentication 提供manager 识别认证器
     * @return 是否匹配
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UserLoginWeXinAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
