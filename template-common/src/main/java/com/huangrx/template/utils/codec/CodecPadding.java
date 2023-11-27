package com.huangrx.template.utils.codec;


import lombok.Getter;

/**
 * 补码方式
 * <p>
 * 补码方式是在分组密码中，当明文长度不是分组长度的整数倍时，需要在最后一个分组中填充一些数据使其凑满一个分组的长度。
 *
 * @author huangrx
 * @since 2023-11-27 20:51
 */
@Getter
public enum CodecPadding {
    /**
     * 无补码
     */
    NO_PADDING("NoPadding"),

    /**
     * 0补码，即不满block长度时使用0填充
     */
    ZERO_PADDING("ZeroPadding"),

    /**
     * This padding for block ciphers is described in 5.2 Block Encryption Algorithms in the W3C's "XML Encryption Syntax and Processing" document.
     */
    ISO10126_PADDING("ISO10126Padding"),

    /**
     * Optimal Asymmetric Encryption Padding scheme defined in PKCS1
     */
    OAEP_PADDING("OAEPPadding"),

    /**
     * The padding scheme described in PKCS #1, used with the RSA algorithm
     */
    PKCS1_PADDING("PKCS1Padding"),

    /**
     * The padding scheme described in RSA Laboratories, "PKCS #5: Password-Based Encryption Standard," version 1.5, November 1993.
     */
    PKCS5_PADDING("PKCS5Padding"),

    /**
     * The padding scheme defined in the SSL Protocol Version 3.0, November 18, 1996, section 5.2.3.2 (CBC block cipher)
     */
    SSL3_PADDING("SSL3Padding");

    private final String value;

    CodecPadding(String value) {
        this.value = value;
    }

}