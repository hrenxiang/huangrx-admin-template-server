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

    private String country;

    private String region;

    private String province;

    private String city;

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
