package com.huangrx.template.constant;

/**
 * @author huangrx
 * @since 2023/11/29 12:53
 */
public class Constants {

    private Constants() {
    }

    /**
     * 通用信息常量
     */
    public static class Message {

        private Message() {
        }

        public static final String I18N_KEY_ERROR_MESSAGE = "错误码i18nKey值定义失败，%s错误码i18nKey值必须以%s开头，当前错误码为%s";
    }

    /**
     * 通用单位常量
     */
    public static class Unit {

        private Unit() {
        }

        // 容量单位
        public static final int KB = 1024;

        public static final int MB = KB * 1024;

        public static final int GB = MB * 1024;

        // 长度单位
        public static final double MILLIMETER = 1.0;

        public static final double CENTIMETER = MILLIMETER * 10;

        public static final double METER = CENTIMETER * 100;

        public static final double KILOMETER = METER * 1000;


        // 重量单位
        public static final double GRAM = 1.0;

        public static final double KILOGRAM = GRAM * 1000;

        public static final double TONNE = KILOGRAM * 1000;
    }

    /**
     * 协议前缀常量
     */
    public static class ProtocolPrefix {

        private ProtocolPrefix() {
        }

        public static final String HTTP = "http://";

        public static final String HTTPS = "https://";
    }

    /**
     * 验证码常量
     */
    public static class Captcha {

        private Captcha() {
        }

        /**
         * 令牌
         */
        public static final String MATH_TYPE = "math";

        /**
         * 令牌前缀
         */
        public static final String CHAR_TYPE = "char";

    }

    /**
     * 验证码常量
     */
    public static class Regex {

        private Regex() {
        }

        /**
         * 正则表达式：验证密码(首字母大写 8位)
         */
        public static final String PASSWORD_EIGHT = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

        /**
         * 正则表达式：验证手机号
         */
        public static final String MOBILE = "^0?(13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])[0-9]{8}$";

        /**
         * 正则表达式：验证邮箱
         */
        public static final String EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    }

    /**
     * 删除标识常量
     */
    public static class DeletedFlag {

        private DeletedFlag() {

        }

        /**
         * 已删除
         */
        public static final String DELETED = "1";

        /**
         * 未删除
         */
        public static final String NOT_DELETE = "0";
    }
}
