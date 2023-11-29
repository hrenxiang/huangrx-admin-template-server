package com.huangrx.template.log.annotation;

import java.lang.annotation.*;

/**
 * 避免 日志切面执行两次，带有此注解的才会执行日志切面
 * <p>
 * 如果要使用，需要修改切点
 * 修改后，只有方法上添加此注解，日志切面才会执行
 *
 * @author hrenxiang
 * @since 2022/8/10 15:34
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CustomLog {
}