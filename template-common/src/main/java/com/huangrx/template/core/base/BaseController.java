package com.huangrx.template.core.base;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;


/**
 * controller 基类
 *
 * @author   huangrx
 * @since   2023-11-25 14:24
 */
@Slf4j
public class BaseController {

    /**
     *
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtil.parseDate(text));
            }
        });
    }

    /**
     * 页面跳转
     */
    public String redirect(String url) {
        return CharSequenceUtil.format("redirect:{}", url);
    }


}
