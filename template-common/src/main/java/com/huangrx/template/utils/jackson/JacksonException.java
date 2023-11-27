package com.huangrx.template.utils.jackson;

/**
 * Jackson exception
 *
 * @author huangrx
 * @since 2023/11/27 14:56
 */
public class JacksonException extends RuntimeException {

    public JacksonException(String message, Exception e) {
        super(message, e);
    }

}
