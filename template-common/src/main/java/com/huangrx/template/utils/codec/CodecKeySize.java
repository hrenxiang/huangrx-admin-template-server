package com.huangrx.template.utils.codec;

import java.nio.charset.StandardCharsets;


/**
 * CodecKeySize是一个枚举类，表示编解码密钥的大小。
 *
 * @author huangrx
 * @since 2023-11-27 20:56
 */
public enum CodecKeySize {

    /**
     * CodecKeySize
     */
    SIX_FOUR(64),

    ONE_TWO_EIGHT(128),

    ONE_NINE_SIX(196),

    TWO_FIVE_SIX(256);

    private final Integer value;

    CodecKeySize(Integer value) {
        this.value = value;
    }

    /**
     * 验证密钥的大小是否符合要求。
     *
     * @param key       密钥
     * @param algorithm 算法
     * @return 如果密钥大小符合要求，则返回true；否则返回false
     */
    public static boolean verifyKeySize(String key, String algorithm) {
        int bitsInByte = 8;
        byte[] bytes = key.getBytes(StandardCharsets.UTF_8);

        if (CodecType.AES.getValue().equals(algorithm)) {
            for (CodecKeySize keySize : CodecKeySize.values()) {
                if (bytes.length == (keySize.value / bitsInByte)) {
                    return Boolean.TRUE;
                }
            }
        } else {
            if (bytes.length == (CodecKeySize.SIX_FOUR.value / bitsInByte)) {
                return Boolean.TRUE;
            }
        }


        return Boolean.FALSE;
    }
}
