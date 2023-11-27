package com.huangrx.template.xss;

import cn.hutool.http.HtmlUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;


/**
 * 反序列化器, 用于将 JSON 数据映射到 Java 对象的工具
 *
 * @author huangrx
 * @since 2023-11-27 21:12
 */
public class JsonHtmlXssTrimSerializer extends JsonDeserializer<String> {

    public JsonHtmlXssTrimSerializer() {
        super();
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext context) throws IOException {
        String value = p.getValueAsString();
        if (value != null) {
            // 去除掉html标签    如果想要转义的话  可使用 HtmlUtil.escape()
            return HtmlUtil.cleanHtmlTag(value);
        }
        return null;
    }

    @Override
    public Class<String> handledType() {
        return String.class;
    }

}