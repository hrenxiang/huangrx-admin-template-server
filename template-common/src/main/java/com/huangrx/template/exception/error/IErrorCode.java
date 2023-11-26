package com.huangrx.template.exception.error;


/**
 * 封装API的错误码
 *
 * @author huangrx
 * @since 2023-11-25 12:34
 */
public interface IErrorCode {

    /**
     * 返回错误码
     *
     * @return 错误码
     */
    int code();

    /**
     * 返回具体的详细错误描述
     *
     * @return 错误描述
     */
    String message();

    /**
     * i18n资源文件的key, 详见messages.properties文件
     *
     * @return key i18n资源文件的key
     */
    String i18nKey();

}