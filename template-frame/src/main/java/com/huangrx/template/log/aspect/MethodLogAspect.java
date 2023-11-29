package com.huangrx.template.log.aspect;

import cn.hutool.json.JSONUtil;
import com.huangrx.template.utils.jackson.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * 方法调用日志切面
 *
 * @author huangrx
 * @since 2023-11-29 23:24
 */
@Aspect
@Component
@Slf4j
public class MethodLogAspect {

    @Pointcut("execution(public * com.huangrx..service.*Service.*(..))")
    public void service() {
    }

    @Around("service()")
    public Object aroundDbService(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();
        log.info("SERVICE : {} ; REQUEST：{} ; RESPONSE : {}", joinPoint.getSignature().toShortString(),
                safeToJson(joinPoint.getArgs()), safeToJson(proceed));
        return proceed;
    }

    @AfterThrowing(value = "service()", throwing = "e")
    public void afterDbServiceThrow(JoinPoint joinPoint, Exception e) {
        log.error("SERVICE : {} ; REQUEST：{} ; EXCEPTION : {}", joinPoint.getSignature().toShortString(),
                safeToJson(joinPoint.getArgs()), e.getMessage());
    }


    /**
     * 安全的打印出Json字符串 因为Jackson的Json格式化要求比较高，可能会报错
     * 如果报错的话 使用Hutool的JSON工具 如果还是报错，直接使用对象的toString方法即可
     * 目的只是为了打印参数和返回值
     * 逻辑上不用太严格
     */
    private String safeToJson(Object o) {
        if (o == null) {
            return "null";
        }
        String json = null;
        try {
            json = JacksonUtil.toJson(o);
        } catch (Exception e) {
            json = JSONUtil.toJsonStr(o);
        } finally {
            if (json == null) {
                json = o.toString();
            }
        }
        return json;
    }


}
