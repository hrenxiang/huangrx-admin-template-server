package com.huangrx.template.utils.codec;

import com.huangrx.template.utils.hex.HexUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

/**
 * 加解密工具类（可以直接用Hutool的SecureUtil）
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
    public static String md5(String res, Boolean isBase64) {
        return messageDigest(res, CodecType.MD5.getValue(), isBase64);
    }

    /**
     * md5加密算法进行加密（不可逆）
     *
     * @param res      需要加密的原文
     * @param key      秘钥
     * @param isBase64 返回结果是否要经过Base64编码，默认16进制
     * @return 加密后的数据
     */
    public static String md5(String res, String key, Boolean isBase64) {
        return keyGeneratorMac(res, CodecType.MD5.getValue(), key, isBase64);
    }

    /**
     * 使用SHA1加密算法进行加密（不可逆）
     *
     * @param res      需要加密的原文
     * @param isBase64 返回结果是否要经过Base64编码，默认16进制
     * @return 加密后的数据
     */
    public static String sha1(String res, Boolean isBase64) {
        return messageDigest(res, CodecType.SHA1.getValue(), isBase64);
    }

    /**
     * 使用SHA1加密算法和相关密钥进行加密（不可逆）
     *
     * @param res      需要加密的原文
     * @param key      秘钥
     * @param isBase64 返回结果是否要经过Base64编码，默认16进制
     * @return 加密后的数据
     */
    public static String sha1(String res, String key, Boolean isBase64) {
        return keyGeneratorMac(res, CodecType.SHA1.getValue(), key, isBase64);
    }

    /**
     * 使用SHA256加密算法进行加密（不可逆）
     *
     * @param res      需要加密的原文
     * @param isBase64 返回结果是否要经过Base64编码，默认16进制
     * @return 加密后的数据
     */
    public static String sha256(String res, Boolean isBase64) {
        return messageDigest(res, CodecType.SHA256.getValue(), isBase64);
    }

    /**
     * 使用SHA256加密算法和相关密钥进行加密（不可逆）
     *
     * @param res      需要加密的原文
     * @param key      秘钥
     * @param isBase64 返回结果是否要经过Base64编码，默认16进制
     * @return 加密后的数据
     */
    public static String sha256(String res, String key, Boolean isBase64) {
        return keyGeneratorMac(res, CodecType.SHA256.getValue(), key, isBase64);
    }

    /**
     * 使用SHA512加密算法进行加密（不可逆）
     *
     * @param res      需要加密的原文
     * @param isBase64 返回结果是否要经过Base64编码，默认16进制
     * @return 加密后的数据
     */
    public static String sha512(String res, Boolean isBase64) {
        return messageDigest(res, CodecType.HMAC_SHA512.getValue(), isBase64);
    }

    /**
     * 使用SHA512加密算法和相关密钥进行加密（不可逆）
     *
     * @param res      需要加密的原文
     * @param key      秘钥
     * @param isBase64 返回结果是否要经过Base64编码，默认16进制
     * @return 加密后的数据
     */
    public static String sha512(String res, String key, Boolean isBase64) {
        return keyGeneratorMac(res, CodecType.HMAC_SHA512.getValue(), key, isBase64);
    }

    // ========================================== 对称加密算法（可逆） =======================================================

    /**
     * 使用AES加密算法经行加密（默认ECB PKCS5Padding，可逆）
     *
     * @param res 需要加密的密文
     * @param key 秘钥
     * @return 加密后的数据
     */
    public static String aesEncode(String res, String key) {
        return keyGeneratorEs(res, CodecType.AES.getValue(), CodecMode.ECB.name(), CodecPadding.PKCS5_PADDING.getValue(), key, true);
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
    public static String aesEncode(String res, String key, String mode, String padding) {
        return keyGeneratorEs(res, CodecType.AES.getValue(), mode, padding, key, true);
    }

    /**
     * 对使用AES加密算法的密文进行解密
     *
     * @param res 需要解密的密文
     * @param key 秘钥
     * @return 加密后的数据
     */
    public static String aesDecode(String res, String key) {
        return keyGeneratorEs(res, CodecType.AES.getValue(), CodecMode.ECB.name(), CodecPadding.PKCS5_PADDING.getValue(), key, false);
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
    public static String aesDecode(String res, String key, String mode, String padding) {
        return keyGeneratorEs(res, CodecType.AES.getValue(), mode, padding, key, false);
    }

    /**
     * 使用DES加密算法经行加密（默认ECB PKCS5Padding，可逆）
     *
     * @param res 需要加密的密文
     * @param key 秘钥
     * @return 加密后的数据
     */
    public static String desEncode(String res, String key) {
        return keyGeneratorEs(res, CodecType.DES.getValue(), CodecMode.ECB.name(), CodecPadding.PKCS5_PADDING.getValue(), key, true);
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
    public static String desEncode(String res, String key, String mode, String padding) {
        return keyGeneratorEs(res, CodecType.DES.getValue(), mode, padding, key, true);
    }

    /**
     * 对使用DES加密算法的密文进行解密
     *
     * @param res 需要解密的密文
     * @param key 秘钥
     * @return 加密后的数据
     */
    public static String desDecode(String res, String key) {
        return keyGeneratorEs(res, CodecType.DES.getValue(), CodecMode.ECB.name(), CodecPadding.PKCS5_PADDING.getValue(), key, false);
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
    public static String desDecode(String res, String key, String mode, String padding) {
        return keyGeneratorEs(res, CodecType.DES.getValue(), mode, padding, key, false);
    }

    /**
     * 使用异或进行加密
     *
     * @param res 需要加密的密文
     * @param key 秘钥
     * @return 加密后的数据
     */
    public static String xorEncode(String res, String key) {
        byte[] bs = res.getBytes();
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) ((bs[i]) ^ key.hashCode());
        }
        return HexUtil.binaryToHex(bs);
    }

    /**
     * 使用异或进行解密
     *
     * @param res 需要解密的密文
     * @param key 秘钥
     * @return 加密后的数据
     */
    public static String xorDecode(String res, String key) {
        byte[] bs = HexUtil.hexToBinary(res);
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
    public static Integer xor(int res, String key) {
        return res ^ key.hashCode();
    }

    /**
     * 使用Base64进行加密
     *
     * @param res 密文
     * @return 加密后的数据
     */
    public static String base64Encode(String res) {
        byte[] bytes = res.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 使用Base64进行解密
     *
     * @param res 密文
     * @return 加密后的数据
     */
    public static String base64Decode(String res) {
        return new String(Base64.getDecoder().decode(res), StandardCharsets.UTF_8);
    }

    /**
     * HmacSHA1 加密
     *
     * @param content 内容
     * @param key     加密秘钥
     * @return 加密后的数据
     */
    public static String getHmacSign(String content, String key) {
        byte[] result = null;
        try {
            //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
            SecretKeySpec signKey = new SecretKeySpec(key.getBytes(), CodecType.HMAC_SHA1.getValue());
            //生成一个指定 Mac 算法 的 Mac 对象
            Mac mac = Mac.getInstance(CodecType.HMAC_SHA1.getValue());
            //用给定密钥初始化 Mac 对象
            mac.init(signKey);
            //完成 Mac 操作
            byte[] rawHmac;
            rawHmac = mac.doFinal(content.getBytes(StandardCharsets.UTF_8));
            result = org.apache.commons.codec.binary.Base64.encodeBase64(rawHmac);

        } catch (NoSuchAlgorithmException | InvalidKeyException | IllegalStateException e) {
            throw new CodecException("HmacSHA1 加密失败！", e);
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
            return HexUtil.binaryToHex(md.digest(resBytes));
        } catch (Exception e) {
            throw new CodecException("使用MessageDigest进行单向加密失败！", e);
        }
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
            return HexUtil.binaryToHex(result);
        } catch (Exception e) {
            throw new CodecException("使用KeyGenerator进行单向/双向加密失败！", e);
        }
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
                throw new CodecException("AES密钥支持128位、196位、以及256位！DES密钥只支持128位！");
            }

            mode = mode == null ? CodecMode.ECB.name() : mode;
            padding = padding == null ? CodecPadding.PKCS5_PADDING.getValue() : padding;
            String resultAlgorithm = algorithm + "/" + mode + "/" + padding;

            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec sks = new SecretKeySpec(keyBytes, algorithm);
            Cipher cipher = Cipher.getInstance(resultAlgorithm);
            if (!CodecMode.ECB.name().equals(mode)) {
                AlgorithmParameters parameters = AlgorithmParameters.getInstance(algorithm);
                // 创建一个 SecureRandom 实例
                SecureRandom secureRandom = SecureRandom.getInstance(algorithm);
                // 生成一个 16 字节的 IV
                secureRandom.nextBytes(keyBytes);
                parameters.init(new IvParameterSpec(keyBytes));
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
            throw new CodecException("使用KeyGenerator双向加密，DES/AES失败", e);
        }
    }

    /**
     * 测试工具类使用 === 无其他作用
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        log.info(CodecUtil.aesEncode("123456", "itMCaD7HcfAnia5c"));
        log.info(CodecUtil.aesDecode("8dXFmsI38cIpuiARXX09wA==", "itMCaD7HcfAnia5c"));
        log.info(xor(123456, "huangrx").toString());
    }

}
