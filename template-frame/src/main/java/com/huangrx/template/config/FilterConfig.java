package com.huangrx.template.config;

import com.huangrx.template.log.filter.TraceIdFilter;
import jakarta.annotation.Resource;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * 过滤器全局配置
 *
 * @author huangrx
 * @since 2023/11/29 22:44
 */
@Configuration
public class FilterConfig {

    @Resource
    private TemplateServerConfig templateServerConfig;

    @Bean
    public FilterRegistrationBean<TraceIdFilter> traceIdFilterRegistrationBean() {
        FilterRegistrationBean<TraceIdFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new TraceIdFilter(templateServerConfig.getTraceRequestIdKey()));
        registration.addUrlPatterns("/*");
        registration.setName("traceIdFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }
}
