package com.huangrx.template.utils.codec;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

/**
 * 加解密工具类
 *
 * @author hrenxiang
 * @since 2022/6/19 18:08
 */
@Slf4j
@UtilityClass
public class CodecUtil {

    // ========================================== 单向散列加密算法（不可逆） =======================================================

    /**
     * md5加密算法进行加密（不可逆）
     *
     * @param res      需要加密的原文
     * @param isBase64 返回结果是否要经过Base64编码，默认16进制
     * @return 加密后的数据
     */
    public static String MD5(String res, Boolean isBase64) {
        return messageDigest(res, CodecType.MD5.name(), isBase64);
    }

    /**
     * md5加密算法进行加密（不可逆）
     *
     * @param res      需要加密的原文
     * @param key      秘钥
     * @param isBase64 返回结果是否要经过Base64编码，默认16进制
     * @return 加密后的数据
     */
    public static String MD5(String res, String key, Boolean isBase64) {
        return keyGeneratorMac(res, CodecType.MD5.name(), key, isBase64);
    }

    /**
     * 使用SHA1加密算法进行加密（不可逆）
     *
     * @param res      需要加密的原文
     * @param isBase64 返回结果是否要经过Base64编码，默认16进制
     * @return 加密后的数据
     */
    public static String SHA1(String res, Boolean isBase64) {
        return messageDigest(res, CodecType.SHA1.name(), isBase64);
    }

    /**
     * 使用SHA1加密算法和相关密钥进行加密（不可逆）
     *
     * @param res      需要加密的原文
     * @param key      秘钥
     * @param isBase64 返回结果是否要经过Base64编码，默认16进制
     * @return 加密后的数据
     */
    public static String SHA1(String res, String key, Boolean isBase64) {
        return keyGeneratorMac(res, CodecType.SHA1.name(), key, isBase64);
    }

    /**
     * 使用SHA256加密算法进行加密（不可逆）
     *
     * @param res      需要加密的原文
     * @param isBase64 返回结果是否要经过Base64编码，默认16进制
     * @return 加密后的数据
     */
    public static String SHA256(String res, Boolean isBase64) {
        return messageDigest(res, CodecType.SHA256.name(), isBase64);
    }

    /**
     * 使用SHA256加密算法和相关密钥进行加密（不可逆）
     *
     * @param res      需要加密的原文
     * @param key      秘钥
     * @param isBase64 返回结果是否要经过Base64编码，默认16进制
     * @return 加密后的数据
     */
    public static String SHA256(String res, String key, Boolean isBase64) {
        return keyGeneratorMac(res, CodecType.SHA256.name(), key, isBase64);
    }

    /**
     * 使用SHA512加密算法进行加密（不可逆）
     *
     * @param res      需要加密的原文
     * @param isBase64 返回结果是否要经过Base64编码，默认16进制
     * @return 加密后的数据
     */
    public static String SHA512(String res, Boolean isBase64) {
        return messageDigest(res, CodecType.HmacSHA512.name(), isBase64);
    }

    /**
     * 使用SHA512加密算法和相关密钥进行加密（不可逆）
     *
     * @param res      需要加密的原文
     * @param key      秘钥
     * @param isBase64 返回结果是否要经过Base64编码，默认16进制
     * @return 加密后的数据
     */
    public static String SHA512(String res, String key, Boolean isBase64) {
        return keyGeneratorMac(res, CodecType.HmacSHA512.name(), key, isBase64);
    }

    // ========================================== 对称加密算法（可逆） =======================================================

    /**
     * 使用AES加密算法经行加密（默认ECB PKCS5Padding，可逆）
     *
     * @param res 需要加密的密文
     * @param key 秘钥
     * @return 加密后的数据
     */
    public static String AESEncode(String res, String key) {
        return keyGeneratorEs(res, CodecType.AES.name(), CodecMode.ECB.name(), CodecPadding.PKCS5Padding.name(), key, true);
    }

    /**
     * 使用AES加密算法经行加密（可逆）
     *
     * @param res     需要加密的密文
     * @param key     秘钥
     * @param mode    加密分组模式
     * @param padding 加密补码模式
     * @return 加密后的数据
     */
    public static String AESEncode(String res, String key, String mode, String padding) {
        return keyGeneratorEs(res, CodecType.AES.name(), mode, padding, key, true);
    }

    /**
     * 对使用AES加密算法的密文进行解密
     *
     * @param res 需要解密的密文
     * @param key 秘钥
     * @return 加密后的数据
     */
    public static String AESDecode(String res, String key) {
        return keyGeneratorEs(res, CodecType.AES.name(), CodecMode.ECB.name(), CodecPadding.PKCS5Padding.name(), key, false);
    }

    /**
     * 对使用AES加密算法的密文进行解密
     *
     * @param res     需要解密的密文
     * @param key     秘钥
     * @param mode    加密分组模式
     * @param padding 加密补码模式
     * @return 加密后的数据
     */
    public static String AESDecode(String res, String key, String mode, String padding) {
        return keyGeneratorEs(res, CodecType.AES.name(), mode, padding, key, false);
    }

    /**
     * 使用DES加密算法经行加密（默认ECB PKCS5Padding，可逆）
     *
     * @param res 需要加密的密文
     * @param key 秘钥
     * @return 加密后的数据
     */
    public static String DESEncode(String res, String key) {
        return keyGeneratorEs(res, CodecType.DES.name(), CodecMode.ECB.name(), CodecPadding.PKCS5Padding.name(), key, true);
    }

    /**
     * 使用DES加密算法经行加密（可逆）
     *
     * @param res     需要加密的密文
     * @param key     秘钥
     * @param mode    加密分组模式
     * @param padding 加密补码模式
     * @return 加密后的数据
     */
    public static String DESEncode(String res, String key, String mode, String padding) {
        return keyGeneratorEs(res, CodecType.DES.name(), mode, padding, key, true);
    }

    /**
     * 对使用DES加密算法的密文进行解密
     *
     * @param res 需要解密的密文
     * @param key 秘钥
     * @return 加密后的数据
     */
    public static String DESDecode(String res, String key) {
        return keyGeneratorEs(res, CodecType.DES.name(), CodecMode.ECB.name(), CodecPadding.PKCS5Padding.name(), key, false);
    }

    /**
     * 对使用DES加密算法的密文进行解密
     *
     * @param res     需要解密的密文
     * @param key     秘钥
     * @param mode    加密分组模式
     * @param padding 加密补码模式
     * @return 加密后的数据
     */
    public static String DESDecode(String res, String key, String mode, String padding) {
        return keyGeneratorEs(res, CodecType.DES.name(), mode, padding, key, false);
    }

    /**
     * 使用异或进行加密
     *
     * @param res 需要加密的密文
     * @param key 秘钥
     * @return 加密后的数据
     */
    public static String XOREncode(String res, String key) {
        byte[] bs = res.getBytes();
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) ((bs[i]) ^ key.hashCode());
        }
        return parseByte2HexStr(bs);
    }

    /**
     * 使用异或进行解密
     *
     * @param res 需要解密的密文
     * @param key 秘钥
     * @return 加密后的数据
     */
    public static String XORDecode(String res, String key) {
        byte[] bs = parseHexStr2Byte(res);
        for (int i = 0; i < Objects.requireNonNull(bs).length; i++) {
            bs[i] = (byte) ((bs[i]) ^ key.hashCode());
        }
        return new String(bs);
    }

    /**
     * 直接使用异或（第一调用加密，第二次调用解密）
     *
     * @param res 密文
     * @param key 秘钥
     * @return 加密后的数据
     */
    public static int XOR(int res, String key) {
        return res ^ key.hashCode();
    }

    /**
     * 使用Base64进行加密
     *
     * @param res 密文
     * @return 加密后的数据
     */
    public static String Base64Encode(String res) {
        byte[] bytes = res.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 使用Base64进行解密
     *
     * @param res 密文
     * @return 加密后的数据
     */
    public static String Base64Decode(String res) {
        return new String(Base64.getDecoder().decode(res), StandardCharsets.UTF_8);
    }

    /**
     * HmacSHA1 加密
     *
     * @param content          内容
     * @param key              加密秘钥
     * @return 加密后的数据
     */
    public static String getHmacSign(String content, String key) {
        byte[] result = null;
        try {
            //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
            SecretKeySpec signKey = new SecretKeySpec(key.getBytes(), CodecType.HmacSHA1.name());
            //生成一个指定 Mac 算法 的 Mac 对象
            Mac mac = Mac.getInstance(CodecType.HmacSHA1.name());
            //用给定密钥初始化 Mac 对象
            mac.init(signKey);
            //完成 Mac 操作
            byte[] rawHmac;
            rawHmac = mac.doFinal(content.getBytes(StandardCharsets.UTF_8));
            result = org.apache.commons.codec.binary.Base64.encodeBase64(rawHmac);

        } catch (NoSuchAlgorithmException | InvalidKeyException | IllegalStateException e) {
            log.error(e.getMessage());
        }
        if (null != result) {
            return new String(result);
        } else {
            return null;
        }
    }

    /**
     * 使用MessageDigest进行单向加密（无密码）
     *
     * @param res       被加密的文本
     * @param algorithm 加密算法名称
     * @param isBase64  返回结果是否要经过Base64编码，默认16进制
     * @return 加密后的数据
     */
    private static String messageDigest(String res, String algorithm, Boolean isBase64) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] resBytes = res.getBytes(StandardCharsets.UTF_8);
            if (Boolean.TRUE.equals(isBase64)) {
                return Base64.getEncoder().encodeToString(md.digest(resBytes));
            }
            return CodecUtil.parseByte2HexStr(md.digest(resBytes));
        } catch (Exception e) {
            log.error("messageDigest error: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 使用KeyGenerator进行单向/双向加密（可设密码）
     *
     * @param res       被加密的原文
     * @param algorithm 加密使用的算法名称
     * @param key       加密使用的秘钥
     * @return 加密后的数据
     */
    private static String keyGeneratorMac(String res, String algorithm, String key, Boolean isBase64) {
        try {
            SecretKey sk;
            if (key == null) {
                KeyGenerator kg = KeyGenerator.getInstance(algorithm);
                sk = kg.generateKey();
            } else {
                byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
                sk = new SecretKeySpec(keyBytes, algorithm);
            }
            Mac mac = Mac.getInstance(algorithm);
            mac.init(sk);
            byte[] result = mac.doFinal(res.getBytes());
            if (Boolean.TRUE.equals(isBase64)) {
                return Base64.getEncoder().encodeToString(result);
            }
            return CodecUtil.parseByte2HexStr(result);
        } catch (Exception e) {
            log.error("keyGeneratorMac error: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 使用KeyGenerator双向加密，DES/AES，注意这里转化为字符串的时候是将2进制转为16进制格式的字符串，不是直接转，因为会出错
     *
     * @param res       加密的原文
     * @param algorithm 加密使用的算法名称
     * @param key       加密的秘钥
     * @param isEncode  true加密，false解密
     * @return 加密后的数据
     */
    private static String keyGeneratorEs(String res, String algorithm, String mode, String padding, String key, Boolean isEncode) {
        try {
            if (!CodecKeySize.verifyKeySize(key, algorithm)) {
                throw new RuntimeException("AES密钥支持128位、196位、以及256位！DES密钥只支持128位！");
            }

            mode = mode == null ? "ECB" : mode;
            padding = padding == null ? "PKCS5Padding" : padding;
            String resultAlgorithm = algorithm + "/" + mode + "/" + padding;

            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec sks = new SecretKeySpec(keyBytes, algorithm);
            Cipher cipher = Cipher.getInstance(resultAlgorithm);
            if (!CodecMode.ECB.name().equals(mode)) {
                AlgorithmParameters parameters = AlgorithmParameters.getInstance(algorithm);
                parameters.init(new IvParameterSpec(key.getBytes()));
                if (Boolean.TRUE.equals(isEncode)) {
                    cipher.init(Cipher.ENCRYPT_MODE, sks, parameters);
                    byte[] resBytes = res.getBytes(StandardCharsets.UTF_8);
                    return Base64.getEncoder().encodeToString(cipher.doFinal(resBytes));
                } else {
                    cipher.init(Cipher.DECRYPT_MODE, sks, parameters);
                    byte[] encryptedBytes = Base64.getDecoder().decode(res);
                    return new String(cipher.doFinal(encryptedBytes), StandardCharsets.UTF_8);
                }
            } else {
                if (Boolean.TRUE.equals(isEncode)) {
                    cipher.init(Cipher.ENCRYPT_MODE, sks);
                    byte[] resBytes = res.getBytes(StandardCharsets.UTF_8);
                    return Base64.getEncoder().encodeToString(cipher.doFinal(resBytes));
                } else {
                    cipher.init(Cipher.DECRYPT_MODE, sks);
                    byte[] encryptedBytes = Base64.getDecoder().decode(res);
                    return new String(cipher.doFinal(encryptedBytes), StandardCharsets.UTF_8);
                }
            }

        } catch (Exception e) {
            log.error("keyGeneratorEs error: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 将二进制转换成16进制字符串
     *
     * @param buf 字节数组
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            // 需要时，可以将16禁止字符串转换为全大写 hex.toUpperCase()
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr 16进制字符串
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.isEmpty()) {
            return new byte[0];
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    private enum CodecType {
        /**
         * {@inheritDoc}
         */
        MD5,
        SHA1,
        SHA256,
        SHA512,
        HmacMD5,
        HmacSHA1,
        HmacSHA256,
        HmacSHA512,
        DES,
        AES
    }

    /**
     * 模式
     * <p>
     * 加密算法模式，是用来描述加密算法（此处特指分组密码，不包括流密码，）在加密时对明文分组的模式，它代表了不同的分组方式
     */
    public enum CodecMode {
        /**
         * 无模式
         */
        NONE,
        /**
         * 密码分组连接模式（Cipher Block Chaining）
         */
        CBC,
        /**
         * 密文反馈模式（Cipher Feedback）
         */
        CFB,
        /**
         * 计数器模式（A simplification of OFB）
         */
        CTR,
        /**
         * Cipher Text Stealing
         */
        CTS,
        /**
         * 电子密码本模式（Electronic CodeBook）
         */
        ECB,
        /**
         * 输出反馈模式（Output Feedback）
         */
        OFB,
        /**
         * Propagating Cipher Block
         */
        PCBC
    }

    /**
     * 补码方式
     *
     * <p>
     * 补码方式是在分组密码中，当明文长度不是分组长度的整数倍时，需要在最后一个分组中填充一些数据使其凑满一个分组的长度。
     */
    public enum CodecPadding {
        /**
         * 无补码
         */
        NoPadding,
        /**
         * 0补码，即不满block长度时使用0填充
         */
        ZeroPadding,
        /**
         * This padding for block ciphers is described in 5.2 Block Encryption Algorithms in the W3C's "XML Encryption Syntax and Processing" document.
         */
        ISO10126Padding,
        /**
         * Optimal Asymmetric Encryption Padding scheme defined in PKCS1
         */
        OAEPPadding,
        /**
         * The padding scheme described in PKCS #1, used with the RSA algorithm
         */
        PKCS1Padding,
        /**
         * The padding scheme described in RSA Laboratories, "PKCS #5: Password-Based Encryption Standard," version 1.5, November 1993.
         */
        PKCS5Padding,
        /**
         * The padding scheme defined in the SSL Protocol Version 3.0, November 18, 1996, section 5.2.3.2 (CBC block cipher)
         */
        SSL3Padding
    }

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

        public static boolean verifyKeySize(String key, String algorithm) {
            byte[] bytes = key.getBytes(StandardCharsets.UTF_8);

            if (CodecType.AES.name().equals(algorithm)) {
                for (CodecKeySize keySize : CodecKeySize.values()) {
                    if (bytes.length == (keySize.value / 8)) {
                        return Boolean.TRUE;
                    }
                }
            } else {
                if (bytes.length == (CodecKeySize.SIX_FOUR.value / 8)) {
                    return Boolean.TRUE;
                }
            }


            return Boolean.FALSE;
        }
    }

}
