package com.huangrx.template.utils.codec;

import lombok.Getter;


/**
 * 编码类型
 *
 * @author huangrx
 * @since 2023-11-27 20:54
 */
@Getter
public enum CodecType {
    /**
     * {@inheritDoc}
     */
    MD5("MD5"),

    SHA1("SHA1"),

    SHA256("SHA256"),

    SHA512("SHA512"),

    HMA_CMD5("HmacMD5"),

    HMAC_SHA1("HmacSHA1"),

    HMAC_SHA256("HmacSHA256"),

    HMAC_SHA512("HmacSHA512"),

    DES("DES"),

    AES("AES");

    private final String value;

    CodecType(String value) {
        this.value = value;
    }

}