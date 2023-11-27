package com.huangrx.template.utils.ip;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * IP地理位置
 *
 * @author   huangrx
 * @since   2023-11-26 22:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IpRegion {

    private static final String UNKNOWN = "未知";

    /**
     * 国家
     */
    private String country;
    /**
     * 地区
     */
    private String region;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 运营商
     */
    private String isp;

    public IpRegion(String province, String city) {
        this.province = province;
        this.city = city;
    }

    public String briefLocation() {
       return String.format("%s %s",
           CharSequenceUtil.nullToDefault(province, UNKNOWN),
           CharSequenceUtil.nullToDefault(city, UNKNOWN)).trim();
    }

}
