package com.huangrx.template.customize.async;

import cn.hutool.extra.spring.SpringUtil;
import com.huangrx.template.enums.common.LoginStatusEnum;
import com.huangrx.template.exception.ApiException;
import com.huangrx.template.exception.error.ErrorCode;
import com.huangrx.template.po.SysLoginInfo;
import com.huangrx.template.po.SysOperationLog;
import com.huangrx.template.service.ISysLoginInfoService;
import com.huangrx.template.service.ISysOperationLogService;
import com.huangrx.template.utils.ServletHolderUtil;
import com.huangrx.template.utils.ip.IpRegionUtil;
import com.huangrx.template.utils.ip.IpUtil;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * <p> 异步工厂，产生任务使用 </p>
 *
 * @author huangrx
 * @since 2023/12/11 19:48
 */
@Slf4j
public class AsyncTaskFactory {

    private AsyncTaskFactory() {
    }

    /**
     * 记录登录信息
     *
     * @param username        用户名
     * @param loginStatusEnum 状态
     * @param message         消息
     * @return 任务task
     */
    public static Runnable loginInfoTask(final String username, final LoginStatusEnum loginStatusEnum, final String message) {
        // 优化一下这个类
        HttpServletRequest request = ServletHolderUtil.getRequest();
        if (Optional.ofNullable(request).isEmpty()) {
            throw new ApiException(ErrorCode.Internal.REQUEST_OBJECT_NULL);
        }
        final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader(HttpHeaders.USER_AGENT));
        // 获取客户端浏览器
        final String browser = userAgent.getBrowser() != null ? userAgent.getBrowser().getName() : "";
        final String ip = IpUtil.getClientIp(ServletHolderUtil.getRequest());
        final String address = IpRegionUtil.getBriefLocationByIp(ip);
        // 获取客户端操作系统
        final String os = userAgent.getOperatingSystem() != null ? userAgent.getOperatingSystem().getName() : "";

        log.info("ip: {}, address: {}, username: {}, loginStatusEnum: {}, message: {}", ip, address, username,
                loginStatusEnum, message);
        return () -> {
            // 封装对象
            SysLoginInfo loginInfo = new SysLoginInfo();
            loginInfo.setUsername(username);
            loginInfo.setIpAddress(ip);
            loginInfo.setLoginLocation(address);
            loginInfo.setBrowser(browser);
            loginInfo.setOperationSystem(os);
            loginInfo.setMsg(message);
            loginInfo.setLoginTime(LocalDateTime.now());
            loginInfo.setStatus(loginStatusEnum.value());
            // 插入数据
            SpringUtil.getBean(ISysLoginInfoService.class).save(loginInfo);
        };
    }

    /**
     * 操作日志记录
     *
     * @param operationLog 操作日志信息
     * @return 任务task
     */
    public static Runnable recordOperationLog(final SysOperationLog operationLog) {
        return () -> {
            // 远程查询操作地点
            operationLog.setOperatorLocation(IpRegionUtil.getBriefLocationByIp(operationLog.getOperatorIp()));
            SpringUtil.getBean(ISysOperationLogService.class).save(operationLog);
        };
    }

}
