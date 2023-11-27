package com.huangrx.template.core.dto;

import com.huangrx.template.exception.ApiException;
import com.huangrx.template.exception.error.ErrorCode;
import lombok.Data;

/**
 * 通用返回对象
 *
 * @author hrenxiang
 * @since 2022-04-24 9:48 PM
 */
@Data
public class ResponseDTO<T> {

    /**
     * 状态码
     */
    private long code;
    /**
     * 描述信息
     */
    private String message;
    /**
     * 响应数据
     */
    private T data;

    private ResponseDTO() {
    }

    private ResponseDTO(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     */
    public static <T> ResponseDTO<T> success() {
        return new ResponseDTO<>(ErrorCode.SUCCESS.code(), ErrorCode.SUCCESS.message(), null);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> ResponseDTO<T> success(T data) {
        return new ResponseDTO<>(ErrorCode.SUCCESS.code(), ErrorCode.SUCCESS.message(), data);
    }

    /**
     * 失败返回结果
     */
    public static <T> ResponseDTO<T> failed() {
        return new ResponseDTO<>(ErrorCode.FAILED.code(), ErrorCode.FAILED.message(), null);
    }

    /**
     * 失败返回结果
     *
     * @param data 携带的数据
     */
    public static <T> ResponseDTO<T> failed(T data) {
        return new ResponseDTO<>(ErrorCode.FAILED.code(), ErrorCode.FAILED.message(), data);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     */
    public static <T> ResponseDTO<T> failed(ErrorCode errorCode) {
        return new ResponseDTO<>(errorCode.code(), errorCode.message(), null);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     * @param data      携带的数据
     */
    public static <T> ResponseDTO<T> failed(ErrorCode errorCode, T data) {
        return new ResponseDTO<>(errorCode.code(), errorCode.message(), data);
    }

    /**
     * 失败返回结果
     *
     * @param exception 自定义异常
     */
    public static <T> ResponseDTO<T> failed(ApiException exception) {
        return new ResponseDTO<>(exception.getErrorCode().code(), exception.getMessage(), null);
    }

    /**
     * 失败返回结果
     *
     * @param exception 自定义异常
     */
    public static <T> ResponseDTO<T> failed(ApiException exception, T data) {
        return new ResponseDTO<>(exception.getErrorCode().code(), exception.getMessage(), data);
    }

}
