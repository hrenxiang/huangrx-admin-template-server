package com.huangrx.template.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 读取项目相关配置
 *
 * @author   huangrx
 * @since   2023-11-27 21:46
 */
@Data
@Component
@ConfigurationProperties(prefix = "template")
public class TemplateServerConfig {

    /**
     * 项目名称
     */
    private String name;

    /**
     * 版本
     */
    private String version;

    /**
     * 版权年份
     */
    private String copyrightYear;

    /**
     * 实例演示开关
     */
    private boolean demoEnabled;

    /**
     * 上传路径
     */
    private String fileBaseDir;

    /**
     * 获取地址开关
     */
    private boolean addressEnabled;

    /**
     * 验证码类型
     */
    private String captchaType;

    /**
     * rsa private key  静态属性的注入！！ set方法一定不能是static 方法
     */
    private String rsaPrivateKey;

    private String apiPrefix;

    /**
     * 日志追踪请求ID的key
     */
    private String traceRequestIdKey;

}