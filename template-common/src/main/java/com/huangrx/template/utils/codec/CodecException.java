package com.huangrx.template.utils.codec;

/**
 * codec exception
 *
 * @author huangrx
 * @since 2023/11/27 14:56
 */
public class CodecException extends RuntimeException {

    public CodecException(String message) {
        super("编解码异常：" + message);
    }

    public CodecException(String message, Exception e) {
        super("编解码异常：" + message, e);
    }

}