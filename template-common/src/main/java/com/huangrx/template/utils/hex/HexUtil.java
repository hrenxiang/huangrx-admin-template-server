package com.huangrx.template.utils.hex;

import java.math.BigInteger;

/**
 * 进制转换工具类
 *
 * @author huangrx
 * @since 2023/11/29 10:12
 */
public class HexUtil {

    private static final int BINARY_RADIX = 2;

    private static final int OCTAL_RADIX = 8;

    private static final int HEX_RADIX = 16;

    private static final int BYTE_SIZE = 8;

    private HexUtil() {
    }

    /**
     * 二进制转换为八进制
     *
     * @param binaryStr 二进制字符串
     * @return 八进制字符串
     */
    public static String parseBinaryToOctal(String binaryStr) {
        int decimal = Integer.parseInt(binaryStr, BINARY_RADIX);
        return Integer.toOctalString(decimal);
    }

    /**
     * 二进制转换为十进制
     *
     * @param binaryStr 二进制字符串
     * @return 十进制数字
     */
    public static Integer parseBinaryToDecimal(String binaryStr) {
        return Integer.parseInt(binaryStr, BINARY_RADIX);
    }

    /**
     * 二进制转换为十六进制
     *
     * @param binaryStr 二进制字符串
     * @return 十六进制字符串
     */
    public static String parseBinaryToHexadecimal(String binaryStr) {
        int decimal = Integer.parseInt(binaryStr, BINARY_RADIX);
        return Integer.toHexString(decimal).toUpperCase();
    }

    /**
     * 八进制转换为二进制
     *
     * @param octalStr 八进制字符串
     * @return 二进制字符串
     */
    public static String parseOctalToBinary(String octalStr) {
        int decimal = Integer.parseInt(octalStr, OCTAL_RADIX);
        return Integer.toBinaryString(decimal);
    }

    /**
     * 八进制转换为十进制
     *
     * @param octalStr 八进制字符串
     * @return 十进制数字
     */
    public static Integer parseOctalToDecimal(String octalStr) {
        return Integer.parseInt(octalStr, OCTAL_RADIX);
    }

    /**
     * 八进制转换为十六进制
     *
     * @param octalStr 八进制字符串
     * @return 十六进制字符串
     */
    public static String parseOctalToHexadecimal(String octalStr) {
        int decimal = Integer.parseInt(octalStr, OCTAL_RADIX);
        return Integer.toHexString(decimal).toUpperCase();
    }

    /**
     * 十进制转换为二进制
     *
     * @param decimal 十进制数字
     * @return 二进制字符串
     */
    public static String parseDecimalToBinary(Integer decimal) {
        return Integer.toBinaryString(decimal);
    }

    /**
     * 十进制转换为八进制
     *
     * @param decimal 十进制数字
     * @return 八进制字符串
     */
    public static String parseDecimalToOctal(Integer decimal) {
        return Integer.toOctalString(decimal);
    }

    /**
     * 十进制转换为十六进制
     *
     * @param decimal 十进制数字
     * @return 十六进制字符串
     */
    public static String parseDecimalToHexadecimal(Integer decimal) {
        return Integer.toHexString(decimal).toUpperCase();
    }

    /**
     * 十六进制转换为二进制
     *
     * @param hexStr 十六进制字符串
     * @return 二进制字符串
     */
    public static String parseHexadecimalToBinary(String hexStr) {
        int decimal = Integer.parseInt(hexStr, HEX_RADIX);
        return Integer.toBinaryString(decimal);
    }

    /**
     * 十六进制转换为八进制
     *
     * @param hexStr 十六进制字符串
     * @return 八进制字符串
     */
    public static String parseHexadecimalToOctal(String hexStr) {
        int decimal = Integer.parseInt(hexStr, HEX_RADIX);
        return Integer.toOctalString(decimal);
    }

    /**
     * 十六进制转换为十进制
     *
     * @param hexStr 十六进制字符串
     * @return 十进制数字
     */
    public static Integer parseHexadecimalToDecimal(String hexStr) {
        return Integer.parseInt(hexStr, HEX_RADIX);
    }

    /**
     * 十六进制转换为字节数组
     *
     * @param hexString 十六进制字符串
     * @return 字节数组
     */
    public static byte[] hexToBinary(String hexString) {
        int ratio = 2;
        if (hexString.length() % ratio != 0) {
            throw new IllegalArgumentException("Hex string length should be even");
        }

        int len = hexString.length() / ratio;
        byte[] binaryData = new byte[len];

        for (int i = 0; i < len; i++) {
            int index = i * ratio;
            String hexByte = hexString.substring(index, index + ratio);
            binaryData[i] = (byte) Integer.parseInt(hexByte, HEX_RADIX);
        }

        return binaryData;
    }

    public static String binaryToHex(byte[] binaryData) {
        BigInteger bigInt = new BigInteger(1, binaryData);
        String hexString = bigInt.toString(HEX_RADIX);

        // Ensure leading zeroes are added if necessary
        int paddingLength = (binaryData.length * BYTE_SIZE) - hexString.length();
        if (paddingLength > 0) {
            hexString = "0".repeat(paddingLength) + hexString;
        }

        return hexString.toUpperCase();
    }
}
