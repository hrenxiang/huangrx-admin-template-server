package com.huangrx.template.security.config;

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
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
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
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private IUserService userService;

    @Resource
    private RedisService redisService;

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
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "https://blog.huangrx.cn", "https://www.huangrx.cn"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        log.info("SecurityConfig ------ 自定义Security相关配置开始");

        // 配置相关公共注入资源
        InjectionSourceConfig.instance().setPasswordEncoder(passwordEncoder());
        InjectionSourceConfig.instance().setUserService(userService);
        InjectionSourceConfig.instance().setRedisService(redisService);

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity
                .authorizeRequests();

        //不需要保护的资源路径允许访问
        for (String url : ignoreUrlsConfig().getUrls()) {
            registry.antMatchers(url).permitAll();
        }

        // 任何请求需要身份认证
        registry.and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                // 因基于token所以不使用session  又因不使用session关闭跨站请求防护
                .and()
                .cors()
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 自定义权限拒绝处理类
                .and()
                .exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler())
                .authenticationEntryPoint(restAuthenticationEntryPoint())
                // 自定义权限拦截器JWT过滤器
                .and()
                .addFilterAfter(getUserAuthenticationFilter(), ExceptionTranslationFilter.class)
                // 添加JWT filter
                .addFilterBefore(jwtAuthenticationTokenFilter(), ExceptionTranslationFilter.class);
    }

    /**
     * 身份认证接口
     *
     * @param auth auth
     * @throws Exception 异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    /**
     * 获取用户 authentication 过滤器
     *
     * @return 过滤器 UserAuthenticationFilter
     */
    private UserAuthenticationFilter getUserAuthenticationFilter() {
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
