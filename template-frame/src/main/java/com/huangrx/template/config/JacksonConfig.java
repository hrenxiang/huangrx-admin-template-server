package com.huangrx.template.config;

import com.huangrx.template.xss.JsonHtmlXssTrimSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.TimeZone;


/**
 * Jackson配置
 *
 * <p>
 * 这种配置方式会覆盖 yml中的jackson配置， 使用下面的customize配置则不会
 * '@'Bean
 * public Jackson2ObjectMapperBuilder objectMapperBuilder() {
 * * * * Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
 * * * * builder.deserializers(new JsonHtmlXssTrimSerializer());
 * * * * return builder;
 * }
 * </p>
 *
 * @author huangrx
 * @since 2023-11-27 21:12
 */
@Configuration
public class JacksonConfig implements Jackson2ObjectMapperBuilderCustomizer {
    @Override
    public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
        // 防XSS脚本注入
        jacksonObjectMapperBuilder.deserializers(new JsonHtmlXssTrimSerializer());
        // 默认时区配置
        jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault());
    }

}