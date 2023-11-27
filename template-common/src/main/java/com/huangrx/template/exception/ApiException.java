package com.huangrx.template.exception;

import cn.hutool.core.text.CharSequenceUtil;
import com.huangrx.template.exception.error.IErrorCode;
import com.huangrx.template.utils.i18n.MessageUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;


/**
 * 统一异常类
 *
 * @author huangrx
 * @since 2023-11-25 12:24
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class ApiException extends RuntimeException {

    private IErrorCode errorCode;

    private String message;

    private String i18nMessage;

    /**
     * 如果有一些特殊的数据  可以放在这个payload里面
     * 有时候错误的返回信息太少  不便前端处理的话  可以放在这个payload字段当中
     * 比如你做了一个大批量操作，操作ID为1~10的实体， 其中1~5成功   6~10失败
     * 你可以将这些相关信息放在这个payload中
     */
    private HashMap<String, Object> payload;

    public ApiException(IErrorCode errorCode) {
        fillErrorCode(errorCode);
    }

    public ApiException(IErrorCode errorCode, Object... args) {
        fillErrorCode(errorCode, args);
    }

    /**
     * 注意  如果是try catch的情况下捕获异常 并转为ApiException的话  一定要填入Throwable e
     *
     * @param e         捕获到的原始异常
     * @param errorCode 错误码
     * @param args      错误详细信息参数
     */
    public ApiException(Throwable e, IErrorCode errorCode, Object... args) {
        super(e);
        fillErrorCode(errorCode, args);
    }

    public ApiException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    public ApiException(String message, IErrorCode errorCode, Throwable e) {
        super(message, e);
        this.message = message;
        this.errorCode = errorCode;
    }

    private void fillErrorCode(IErrorCode errorCode, Object... args) {
        this.errorCode = errorCode;
        this.message = CharSequenceUtil.format(errorCode.message(), args);

        try {
            this.i18nMessage = MessageUtils.message(errorCode.i18nKey(), args);
        } catch (Exception e) {
            log.error("could not found i18nMessage entry for key: " + errorCode.i18nKey());
        }
    }

    @Override
    public String getMessage() {
        return i18nMessage != null ? i18nMessage : message;
    }

    @Override
    public String getLocalizedMessage() {
        return i18nMessage != null ? i18nMessage : message;
    }

}
