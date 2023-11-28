package com.huangrx.template.security.config;

import com.huangrx.template.security.service.ILoginService;
import com.huangrx.template.security.filter.JwtAuthenticationTokenFilter;
import com.huangrx.template.security.filter.UserAuthenticationFilter;
import com.huangrx.template.security.handler.RestAuthenticationEntryPoint;
import com.huangrx.template.security.handler.RestfulAccessDeniedHandler;
import com.huangrx.template.security.handler.UserLoginAuthenticationFailureHandler;
import com.huangrx.template.security.handler.UserLoginAuthenticationSuccessHandler;
import com.huangrx.template.security.provider.UserLoginNormalAuthenticationProvider;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


/**
 * 对SpringSecurity的配置的扩展，支持自定义白名单资源路径和查询用户逻辑
 *
 * @author hrenxiang
 * @since 2022-04-24 9:48 PM
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Resource
    private ILoginService loginService;

    /**
     * 强散列哈希加密实现
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public AuthenticationProvider userLoginNormalAuthenticationProvider() {
        return new UserLoginNormalAuthenticationProvider();
    }

    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
        return new RestfulAccessDeniedHandler();
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public IgnoreUrlsConfig ignoreUrlsConfig() {
        return new IgnoreUrlsConfig();
    }

    @Bean
    public UserLoginAuthenticationSuccessHandler successHandler() {
        return new UserLoginAuthenticationSuccessHandler();
    }

    @Bean
    public UserLoginAuthenticationFailureHandler failureHandler() {
        return new UserLoginAuthenticationFailureHandler();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        log.info("SecurityConfig ------ 自定义跨域设置");

        CorsConfiguration config = new CorsConfiguration();
        // 允许的域，可以设置为具体的域名或 "*" 表示所有域都允许访问
        config.addAllowedOrigin("*");
        // 允许的方法，比如 GET、POST 等
        config.addAllowedMethod("*");
        // 允许的头信息
        config.addAllowedHeader("*");
        // 是否支持凭证（跨域请求携带 cookie 等凭证信息）
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("SecurityConfig ------ Security相关配置初始化");

        InjectionSourceConfig.instance().setLoginService(loginService);
        InjectionSourceConfig.instance().setPasswordEncoder(passwordEncoder());

        // 需要放行的接口
        List<String> permitUrls = ignoreUrlsConfig().getUrls();

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(permitUrls.toArray(new String[0]))
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                // 禁用Basic明文验证
                .httpBasic(AbstractHttpConfigurer::disable)
                // 前后端分离，由于同源策略，不需要设置csrf防护机制
                .csrf(AbstractHttpConfigurer::disable)
                // 禁用默认登录页
                .formLogin(AbstractHttpConfigurer::disable)
                // 禁用默认登出页
                .logout(AbstractHttpConfigurer::disable)
                // 创建策略为“无状态”，这意味着每个请求都不会创建或使用会话，使得服务变得无状态化。
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 设置异常accessDenied and EntryPoint
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(restfulAccessDeniedHandler())
                        .authenticationEntryPoint(restAuthenticationEntryPoint()))
                // 自定义用户认证过滤器链
                .addFilterBefore(userAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                // 自定义权限拦截器JWT过滤器
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 获取用户 authentication 过滤器
     *
     * @return 过滤器 UserAuthenticationFilter
     */
    private UserAuthenticationFilter userAuthenticationFilter() {
        log.info("UserAuthenticationFilter ------ 身份认证处理过滤器初始化开始");
        UserAuthenticationFilter userAuthenticationFilter = new UserAuthenticationFilter(successHandler(), failureHandler());
        List<AuthenticationProvider> providerList = List.of(
                new UserLoginNormalAuthenticationProvider());

        ProviderManager providerManager = new ProviderManager(providerList);
        userAuthenticationFilter.setAuthenticationManager(providerManager);
        log.info("SecurityConfig ------ 添加用户认证管理器集合（使用ProviderManager）");
        return userAuthenticationFilter;
    }

}
