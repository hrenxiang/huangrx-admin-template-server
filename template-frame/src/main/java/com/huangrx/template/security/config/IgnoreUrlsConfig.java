package com.huangrx.template.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于配置白名单资源路径
 *
 * @author hrenxiang
 * @since 2022-04-24 9:48 PM
 */
@Data
@ConfigurationProperties(prefix = "security.ignored")
public class IgnoreUrlsConfig {

    private List<String> urls = new ArrayList<>();

}
