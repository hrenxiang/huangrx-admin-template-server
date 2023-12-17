package com.huangrx.template.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;


/**
 * SimpleGrantedAuthority 反序列化器（redis到object）
 * 
 * @author   huangrx
 * @since   2023-12-17 14:47
 */
public class SimpleGrantedAuthorityDeserializer extends StdDeserializer<SimpleGrantedAuthority> {

    public SimpleGrantedAuthorityDeserializer() {
        super(SimpleGrantedAuthority.class);
    }
    @Override
    public SimpleGrantedAuthority deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode jsonNode = p.getCodec().readTree(p);
        JsonNode authority = jsonNode.get("authority");
        if (authority != null && !authority.isNull()) {
            return new SimpleGrantedAuthority(authority.asText());
        }
//        Iterator<JsonNode> elements = jsonNode.elements();
//        while (elements.hasNext()) {
//            JsonNode next = elements.next();
//            JsonNode authority = next.get("authority");
//            if(ObjectUtils.isEmpty(authority)){
//                continue;
//            }
//
//             return new SimpleGrantedAuthority(authority.asText());
//        }
        return  null;
 
    }
}