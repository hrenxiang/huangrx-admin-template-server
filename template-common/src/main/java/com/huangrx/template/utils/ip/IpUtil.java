package com.huangrx.template.utils.ip;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.text.StrPool;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;


/**
 * Ip校验器
 *
 * @author   huangrx
 * @since   2023-11-26 22:32
 */
@Slf4j
public class IpUtil {

    public static final String INNER_IP_REGEX = "^(127\\.0\\.0\\.\\d{1,3})|(localhost)|(10\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})|(172\\.((1[6-9])|(2\\d)|(3[01]))\\.\\d{1,3}\\.\\d{1,3})|(192\\.168\\.\\d{1,3}\\.\\d{1,3})$";

    public static final Pattern INNER_IP_PATTERN = Pattern.compile(INNER_IP_REGEX);

    public static final String X_FORWARDED_FOR = "x-forwarded-for";
    public static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
    public static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
    public static final String X_REAL_IP = "X-Real-IP";
    public static final String IPV4_LOCAL_LOOPBACK_ADDRESS = "127.0.0.1";
    public static final String IPV6_LOCAL_LOOPBACK_ADDRESS = "0:0:0:0:0:0:0:1";
    public static final String UNKNOWN = "unknown";

    private IpUtil() {
    }

    /**
     * 获取客户端IP
     *
     * @param request 请求对象
     * @return IP地址
     */
    public static String getClientIp(HttpServletRequest request) {
        if (request == null) {
            return UNKNOWN;
        }
        String ip = request.getHeader(X_FORWARDED_FOR);
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(PROXY_CLIENT_IP);
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(X_FORWARDED_FOR);
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(WL_PROXY_CLIENT_IP);
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(X_REAL_IP);
        }

        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return IPV6_LOCAL_LOOPBACK_ADDRESS.equals(ip) ? IPV4_LOCAL_LOOPBACK_ADDRESS : getMultistageReverseProxyIp(ip);
    }

    public static boolean isInnerIp(String ip) {
        return INNER_IP_PATTERN.matcher(ip).matches() || isLocalHost(ip);
    }

    public static boolean isLocalHost(String ipAddress) {
        InetAddress ia = null;
        try {
            InetAddress ad = InetAddress.getByName(ipAddress);
            byte[] ip = ad.getAddress();
            ia = InetAddress.getByAddress(ip);
        } catch (UnknownHostException e) {
            log.error("解析Ip失败", e);
        }
        if (ia == null) {
            return false;
        }
        return ia.isSiteLocalAddress() || ia.isLoopbackAddress();
    }


    public static boolean isValidIpv4(String inetAddress) {
        return Validator.isIpv4(inetAddress);
    }

    public static boolean isValidIpv6(String inetAddress) {
        return Validator.isIpv6(inetAddress);
    }

    /**
     * 从多级反向代理中获得第一个非unknown IP地址
     *
     * @param ip 获得的IP地址
     * @return 第一个非unknown IP地址
     */
    public static String getMultistageReverseProxyIp(String ip) {
        // 多级反向代理检测
        if (ip != null && ip.contains(StrPool.COMMA)) {
            final String[] ips = ip.trim().split(StrPool.COMMA);
            for (String subIp : ips) {
                if (!isUnknown(subIp)) {
                    ip = subIp;
                    break;
                }
            }
        }
        return ip;
    }

    /**
     * 检测给定字符串是否为未知，多用于检测HTTP请求相关
     *
     * @param checkString 被检测的字符串
     * @return 是否未知
     */
    public static boolean isUnknown(String checkString) {
        return StringUtils.isBlank(checkString) || UNKNOWN.equalsIgnoreCase(checkString);
    }

}
